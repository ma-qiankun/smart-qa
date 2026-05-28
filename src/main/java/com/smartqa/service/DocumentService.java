package com.smartqa.service;

import com.smartqa.entity.Document;
import com.smartqa.entity.User;
import com.smartqa.infrastructure.parser.DocumentParser;
import com.smartqa.repository.DocumentRepository;
import com.smartqa.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final List<DocumentParser> parsers;
    private final Path uploadPath;

    public DocumentService(
            DocumentRepository documentRepository,
            UserRepository userRepository,
            List<DocumentParser> parsers,
            @Value("${smartqa.upload-dir:uploads}") String uploadDir) throws IOException {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.parsers = parsers;
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadPath);
    }

    public Map<String, Long> upload(MultipartFile file, String title, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在: " + userId));

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String storedFilename = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(storedFilename);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        Document document = new Document();
        document.setTitle(title);
        document.setFilePath(storedFilename);
        document.setFileType(extension);
        document.setStatus("PROCESSING");
        document.setUploadedBy(user);
        documentRepository.save(document);

        parseDocumentAsync(document.getId(), storedFilename, extension);

        return Map.of("documentId", document.getId());
    }

    @Async("documentParseExecutor")
    public void parseDocumentAsync(Long documentId, String storedFilename, String extension) {
        DocumentParser parser = parsers.stream()
                .filter(p -> p.supports(extension))
                .findFirst()
                .orElse(null);

        if (parser == null) {
            log.warn("No parser found for extension: {}", extension);
            documentRepository.findById(documentId).ifPresent(doc -> {
                doc.setStatus("FAILED");
                documentRepository.save(doc);
            });
            return;
        }

        Path filePath = uploadPath.resolve(storedFilename);
        try (InputStream in = Files.newInputStream(filePath)) {
            String content = parser.parse(in);
            documentRepository.findById(documentId).ifPresent(doc -> {
                doc.setContent(content);
                doc.setStatus("DONE");
                documentRepository.save(doc);
            });
            log.info("Document {} parsed successfully, content length: {}", documentId, content.length());
        } catch (Exception e) {
            log.error("Failed to parse document {}", documentId, e);
            documentRepository.findById(documentId).ifPresent(doc -> {
                doc.setStatus("FAILED");
                documentRepository.save(doc);
            });
        }
    }
}
