    package com.foundation.crud.exception;

    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ResponseStatus;


    /**
     * Custom exception class for resource not found.
     * Extends the {@link RuntimeException} class.
     * This exception is used to indicate that a requested resource is not found.
     * It carries a custom error message that can be used for error handling and reporting.
     */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public class ResourceNotFound extends RuntimeException {

        /**
         * Constructs a new {@code ResourceNotFound} instance with the provided error message.
         * @param message the error message describing the resource not found situation.
         */
        public ResourceNotFound(String message) {
            super(message);
        }
    }
