package com.example.eshop.exception;

import com.example.eshop.enums.AppEnums;
import com.example.eshop.utils.EShopResponse;
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
    public ResponseEntity<EShopResponse<Object>> handleDataNotFoundException(DataNotFoundException ex) {
        return new ResponseEntity<>(EShopResponse.failure(AppEnums.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles processing or business logic exceptions.
     *
     * @param ex the ProcessingException
     * @return standardized API response
     */
    @ExceptionHandler(ProcessingError.class)
    public ResponseEntity<EShopResponse<Object>> handleProcessingException(ProcessingError ex) {
        return new ResponseEntity<>(EShopResponse.failure(AppEnums.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles all other unhandled exceptions.
     *
     * @param ex the Exception
     * @return standardized API response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<EShopResponse<Object>> handleGenericException(Exception ex) {
        return new ResponseEntity<>(EShopResponse.failure(AppEnums.FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
