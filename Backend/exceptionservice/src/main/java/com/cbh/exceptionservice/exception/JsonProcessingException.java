package com.cbh.exceptionservice.exception;

import org.springframework.http.HttpStatus;

import com.cbh.exceptionservice.globalexception.GenericException;

/**
 * Exception thrown when there is an issue with JSON processing, resulting in an internal server error.
 * Inherits from {@link GenericException}.
 *
 * @see GenericException
 */
public class JsonProcessingException extends GenericException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a JsonProcessingException with the specified field name and message.
     *
     * @param fieldName The name of the field associated with the exception.
     * @param message   A descriptive message providing more information about the exception.
     */
    public JsonProcessingException(String fieldName, String message) {
        super(fieldName, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
