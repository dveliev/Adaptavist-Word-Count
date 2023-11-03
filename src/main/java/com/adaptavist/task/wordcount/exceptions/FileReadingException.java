package com.adaptavist.task.wordcount.exceptions;

public class FileReadingException extends RuntimeException {
    public FileReadingException(String message) {
        super(message);
    }

    public FileReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
