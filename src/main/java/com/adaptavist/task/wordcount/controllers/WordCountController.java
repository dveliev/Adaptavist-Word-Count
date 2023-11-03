package com.adaptavist.task.wordcount.controllers;

import com.adaptavist.task.wordcount.entities.WordCountResponseBody;
import com.adaptavist.task.wordcount.services.FileReadingService;
import com.adaptavist.task.wordcount.services.FileValidationService;
import com.adaptavist.task.wordcount.services.WordCountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/word-count")
public class WordCountController {
    private final FileValidationService fileValidator;
    private final FileReadingService fileReadingService;
    private final WordCountService wordCountService;

    public WordCountController(FileValidationService fileValidator, FileReadingService fileReadingService, WordCountService wordCountService) {
        this.fileValidator = fileValidator;
        this.fileReadingService = fileReadingService;
        this.wordCountService = wordCountService;
    }

    @PostMapping("/process")
    public ResponseEntity<WordCountResponseBody> processFile(@RequestParam("file") MultipartFile file) {

            fileValidator.validateFile(file);
            String content = fileReadingService.readFileContent(file);
            Map<String, Integer> wordCount = wordCountService.count(content);
            WordCountResponseBody responseBody = new WordCountResponseBody(wordCount, null);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
