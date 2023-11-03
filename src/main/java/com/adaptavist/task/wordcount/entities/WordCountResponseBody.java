package com.adaptavist.task.wordcount.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WordCountResponseBody(Map<String, Integer> wordCount, String errorMessage) { }
