package com.adaptavist.task.wordcount.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
 class WordCountApplicationIntegrationTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testWordCountApplication_withTxtFile() throws Exception {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("request/lorem-ipsum.txt");
        MockMultipartFile file = new MockMultipartFile("file", stream);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/word-count/process").file(file))
                .andExpect(status().is(200))
                .andExpect(content().string(readJsonFile("response/lorem-ipsum.json")));
    }

    @Test
    void testWordCountApplication_withJsonFile()throws Exception {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("request/JSON-test.json");
        MockMultipartFile file = new MockMultipartFile("file", stream);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/word-count/process").file(file))
                .andExpect(status().is(200))
                .andExpect(content().string(readJsonFile("response/json-test.json")));
    }

    @Test
    void testWordCountApplication_withPdfFile() throws Exception {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("request/PDF-test.pdf");
        MockMultipartFile file = new MockMultipartFile("file", stream);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/word-count/process").file(file))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"errorMessage\":\"Invalid file type: application/pdf\"}"));
    }

    @Test
    void testWordCountApplication_withEmptyFile() throws Exception {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("request/empty.txt");
        MockMultipartFile file = new MockMultipartFile("file", stream);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/word-count/process").file(file))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"errorMessage\":\"File is empty\"}"));
    }

    private String readJsonFile(String path) throws IOException {
        var resource = new ClassPathResource(path);
        return Files.readString(resource.getFile().toPath(), UTF_8);
    }

}
