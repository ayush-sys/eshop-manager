package com.example.eshop.exception;

public class ProcessingError extends RuntimeException {
    public ProcessingError(String message) {
        super(message);
    }
}
