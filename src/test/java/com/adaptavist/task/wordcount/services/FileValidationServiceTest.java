package com.adaptavist.task.wordcount.services;

import com.adaptavist.task.wordcount.exceptions.FileReadingException;
import org.apache.tika.Tika;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileValidationServiceTest {

    private FileValidationService fileValidationService;

    @Mock
    private Tika tika;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fileValidationService = new FileValidationService(tika);
    }


    @Test
     void testValidateFile_ValidTextFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, world!".getBytes());
        when(tika.detect(Mockito.any(InputStream.class))).thenReturn("text/plain");

        // Expect no exception
        fileValidationService.validateFile(file);
    }

    @Test
     void testValidateFile_EmptyFile() {
        MockMultipartFile file = new MockMultipartFile("file", "request/empty.txt", "text/plain", new byte[0]);

        assertThrows(FileReadingException.class, () -> fileValidationService.validateFile(file));
    }

    @Test
     void testValidateFile_ErrorDetectingMimeType() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", null, "Hello, world!".getBytes());
        when(tika.detect(Mockito.any(InputStream.class))).thenThrow(new RuntimeException("MIME detection error"));

        assertThrows(FileReadingException.class, () -> fileValidationService.validateFile(file));
    }

    @Test
    void testValidateFile_InvalidMimeType() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "application/pdf", "Invalid file".getBytes());
        when(tika.detect(Mockito.any(InputStream.class))).thenReturn("application/pdf");

        assertThrows(FileReadingException.class, () -> fileValidationService.validateFile(file));
    }
}
