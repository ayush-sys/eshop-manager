package com.example.eshop.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {

    private String status;
    private String message;
    private T data;
    private long timestamp;

    public void successMessage(String message, T data) {
        this.status = HttpStatus.OK.toString();
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public void errorMessage(String message) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        this.message = message;
        this.data = null;
        this.timestamp = System.currentTimeMillis();
    }

}
