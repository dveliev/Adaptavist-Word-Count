package com.adaptavist.task.wordcount.services;

import com.adaptavist.task.wordcount.exceptions.FileReadingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class FileReadingService {
    public String readFileContent(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }
            return content.toString();
        } catch (IOException e) {
            throw new FileReadingException("Error processing the file: " + e.getMessage(), e);
        }

    }
}
