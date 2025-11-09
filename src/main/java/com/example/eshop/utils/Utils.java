package com.example.eshop.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Returns the current date and time in ISO 8601 format.
     */
    public static String getCurrentIsoTime() {
        return LocalDateTime.now().format(ISO_FORMATTER);
    }

}
