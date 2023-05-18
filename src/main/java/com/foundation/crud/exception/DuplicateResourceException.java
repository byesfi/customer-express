package com.foundation.crud.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for duplicate resource.
 * Extends the {@link RuntimeException} class.
 * This exception is used to indicate that a duplicate resource is encountered.
 * It carries a custom error message that can be used for error handling and reporting.
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {

    /**
     * Constructs a new {@code DuplicateResourceException} instance with the provided error message.
     *
     * @param message the error message describing the duplicate resource situation.
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}
