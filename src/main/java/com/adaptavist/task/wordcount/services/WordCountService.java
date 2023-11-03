package com.adaptavist.task.wordcount.services;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WordCountService {
    public Map<String, Integer> count(String content) {
        String[] words = content.split("\\s");
        Map<String, Integer> wordCountMap = countUsage(words);

        return sortDescendingByUsage(wordCountMap);
    }

    private Map<String, Integer> countUsage(String[] words) {
        Map<String, Integer> wordUsageMap = new HashMap<>();


        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!word.isEmpty()) {
                wordUsageMap.put(word, wordUsageMap.getOrDefault(word, 0) + 1);
            }
        }

        return wordUsageMap;
    }

    private Map<String, Integer> sortDescendingByUsage(Map<String, Integer> wordCountMap) {
        return wordCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}

