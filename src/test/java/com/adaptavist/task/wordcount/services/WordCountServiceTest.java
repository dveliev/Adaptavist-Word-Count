package com.adaptavist.task.wordcount.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;

class WordCountServiceTest {

    private WordCountService wordCountService;

    @BeforeEach
    public void setUp() {
        wordCountService = new WordCountService();
    }

    @Test
    void testCount_ValidContent() {
        String content = "This is a test. This is only a test. Testing, testing!";
        Map<String, Integer> wordCountMap = wordCountService.count(content);

        assertEquals(2, wordCountMap.get("this"));
        assertEquals(2, wordCountMap.get("is"));
        assertEquals(2, wordCountMap.get("a"));
        assertEquals(2, wordCountMap.get("test"));
        assertEquals(1, wordCountMap.get("only"));
        assertEquals(2, wordCountMap.get("testing"));
    }

    @Test
    void testCount_EmptyContent() {
        String content = "";
        Map<String, Integer> wordCountMap = wordCountService.count(content);

        assertTrue(wordCountMap.isEmpty());
    }

    @Test
    void testCount_ContentWithSpecialCharacters() {
        String content = "$hello, world! %example";
        Map<String, Integer> wordCountMap = wordCountService.count(content);

        assertEquals(1, wordCountMap.get("hello"));
        assertEquals(1, wordCountMap.get("world"));
        assertEquals(1, wordCountMap.get("example"));
    }
}