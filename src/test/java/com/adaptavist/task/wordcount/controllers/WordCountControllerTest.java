package com.adaptavist.task.wordcount.controllers;

import com.adaptavist.task.wordcount.entities.WordCountResponseBody;
import com.adaptavist.task.wordcount.services.FileReadingService;
import com.adaptavist.task.wordcount.services.FileValidationService;
import com.adaptavist.task.wordcount.services.WordCountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class WordCountControllerTest {

    private WordCountController wordCountController;

    @Mock
    private FileValidationService fileValidator;
    @Mock
    private FileReadingService fileReadingService;
    @Mock
    private WordCountService wordCountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        wordCountController = new WordCountController(fileValidator, fileReadingService, wordCountService);
    }

    @Test
    void testProcessFile_ValidFile() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, world!".getBytes());

        when(fileReadingService.readFileContent(file)).thenReturn("Hello, world!");
        when(wordCountService.count("Hello, world!")).thenReturn(Collections.singletonMap("Hello", 1));

        ResponseEntity<WordCountResponseBody> response = wordCountController.processFile(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().wordCount().get("Hello"));
        assertNull(response.getBody().errorMessage());

        verify(fileValidator, times(1)).validateFile(file);
        verify(fileReadingService, times(1)).readFileContent(file);
        verify(wordCountService, times(1)).count("Hello, world!");
    }
}
