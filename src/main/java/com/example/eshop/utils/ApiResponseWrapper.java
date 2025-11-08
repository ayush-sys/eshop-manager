package com.example.eshop.utils;

import com.example.eshop.enums.AppEnums;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponseWrapper<T> {

    /** The api status. */
    private String status;

    /** The api status message. */
    private String message;

    /** The api data. */
    private T data;

    /** The api response time. */
    private long timestamp;

    /**
     * Prepares the success message.
     *
     * @param message the message
     * @param data    the data
     */
    public void successMessage(AppEnums message, T data) {
        this.status = HttpStatus.OK.name();
        this.message = message.toString();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Prepares the error message.
     *
     * @param message the message
     */
    public void errorMessage(AppEnums message) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.name();
        this.message = message.toString();
        this.data = null;
        this.timestamp = System.currentTimeMillis();
    }

}