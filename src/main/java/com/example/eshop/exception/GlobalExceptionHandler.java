package com.example.eshop.exception;

import com.example.eshop.enums.AppEnums;
import com.example.eshop.utils.ApiResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Global exception handler for all application-wide exceptions. */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles data not found exceptions.
     *
     * @param ex the DataNotFoundException
     * @return standardized API response
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleDataNotFoundException(DataNotFoundException ex) {
        ApiResponseWrapper<Object> response = new ApiResponseWrapper<>();
        response.errorMessage(AppEnums.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles processing or business logic exceptions.
     *
     * @param ex the ProcessingException
     * @return standardized API response
     */
    @ExceptionHandler(ProcessingError.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleProcessingException(ProcessingError ex) {
        ApiResponseWrapper<Object> response = new ApiResponseWrapper<>();
        response.errorMessage(AppEnums.ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles all other unhandled exceptions.
     *
     * @param ex the Exception
     * @return standardized API response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseWrapper<Object>> handleGenericException(Exception ex) {
        ApiResponseWrapper<Object> response = new ApiResponseWrapper<>();
        response.errorMessage(AppEnums.FAILED);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
