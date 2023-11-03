package com.adaptavist.task.wordcount.services;

import com.adaptavist.task.wordcount.exceptions.FileReadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class FileReadingServiceTest {

    private FileReadingService fileReadingService;

    @Mock
    private MockMultipartFile file;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fileReadingService = new FileReadingService();
    }

    @Test
    void testReadFileContent_ValidFile() throws IOException {
        String content = "This is a test file.\nIt contains some text.\n";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        when(file.getInputStream()).thenReturn(inputStream);

        String result = fileReadingService.readFileContent(file);

        assertEquals(content, result);
    }

    @Test
    void testReadFileContent_EmptyFile() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(new byte[0]);
        when(file.getInputStream()).thenReturn(inputStream);

        String result = fileReadingService.readFileContent(file);

        assertEquals("", result);
    }

    @Test
    void testReadFileContent_ErrorReadingFile() throws IOException {
        when(file.getInputStream()).thenThrow(new IOException("File reading error"));

        assertThrows(FileReadingException.class, () -> fileReadingService.readFileContent(file));
    }
}