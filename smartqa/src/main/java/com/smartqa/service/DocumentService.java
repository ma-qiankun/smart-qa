package com.smartqa.service;

import com.smartqa.entity.Document;
import com.smartqa.entity.User;
import com.smartqa.repository.DocumentRepository;
import com.smartqa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final Path uploadPath;

    public DocumentService(
            DocumentRepository documentRepository,
            UserRepository userRepository,
            @Value("${smartqa.upload-dir:uploads}") String uploadDir) throws IOException {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
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

        return Map.of("documentId", document.getId());
    }
}
