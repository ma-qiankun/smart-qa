package com.smartqa.infrastructure.parser;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class WordDocumentParser implements DocumentParser {

    @Override
    public boolean supports(String extension) {
        return ".docx".equalsIgnoreCase(extension) || ".doc".equalsIgnoreCase(extension);
    }

    @Override
    public String parse(InputStream inputStream) {
        try (XWPFDocument document = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            return extractor.getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Word document", e);
        }
    }
}
