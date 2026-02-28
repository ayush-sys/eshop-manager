package com.example.eshop.utils;

import com.example.eshop.enums.AppEnums;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EShopResponse<T> {

    /** The HTTP API status. */
    private String status;

    /** The API status message. */
    private String message;

    /** The API response data. */
    private T data;

    /** The API response timestamp. */
    private String timestamp;

    /** Default constructor. */
    public EShopResponse() {
    }

    /**
     * Constructor with status, message, and data.
     *
     * @param status  the HTTP status
     * @param message the response message
     * @param data    the response data
     */
    public EShopResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = Utils.getCurrentIsoTime();
    }

    /**
     * Creates a success response.
     *
     * @param message the message enum
     * @param data    the response data
     * @return ApiResponseWrapper instance
     */
    public static <T> EShopResponse<T> success(AppEnums message, T data) {
        EShopResponse<T> response = new EShopResponse<>();
        response.setStatus(HttpStatus.OK.name());
        response.setMessage(message.toString());
        response.setData(data);
        response.setTimestamp(Utils.getCurrentIsoTime());
        return response;
    }

    /**
     * Creates a failure or error response.
     *
     * @param message the message enum
     * @return ApiResponseWrapper instance
     */
    public static <T> EShopResponse<T> failure(AppEnums message, String errorDetails) {
        EShopResponse<T> response = new EShopResponse<>();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        response.setMessage(message + (errorDetails != null ? " - " + errorDetails : ""));
        response.setData(null);
        response.setTimestamp(Utils.getCurrentIsoTime());
        return response;
    }

    /**
     * Creates a failure response with default message only.
     */
    public static <T> EShopResponse<T> failure(AppEnums message) {
        return failure(message, null);
    }

}
