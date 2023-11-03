package com.adaptavist.task.wordcount.exceptions;

import com.adaptavist.task.wordcount.entities.WordCountResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class WordCountExceptionHandler {
    @ExceptionHandler({FileReadingException.class, MultipartException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WordCountResponseBody handleFileReadingException(Exception e) {
        return new WordCountResponseBody(null, e.getMessage());
    }
}
