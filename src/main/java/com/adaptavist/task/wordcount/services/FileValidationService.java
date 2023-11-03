package com.adaptavist.task.wordcount.services;

import com.adaptavist.task.wordcount.exceptions.FileReadingException;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class FileValidationService {
    private static final HashSet<String> ALLOWED_MIME_TYPES = new HashSet<>(Arrays.asList(
            "text/plain",
            "text/csv",
            "application/json",
            "application/octet-stream"
    ));
    private final Tika tika;
    public FileValidationService(Tika tika) {
        this.tika = tika;
    }
    public void validateFile(MultipartFile file) {
        String detectedMimeType;

        if (file.isEmpty()) {
            throw new FileReadingException("File is empty");
        }

        try {
            detectedMimeType = tika.detect(file.getInputStream());
        } catch (Exception e) {
            throw new FileReadingException("Error detecting MIME type: " + e.getMessage(), e);
        }

        if (!isValidMimeType(detectedMimeType)) {
            throw new FileReadingException("Invalid file type: " + detectedMimeType);
        }
    }

    private boolean isValidMimeType(String mimeType) {
        return ALLOWED_MIME_TYPES.contains(mimeType);
    }
}