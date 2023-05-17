package com.foundation.crud.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceNotFoundTest {

    @Test
    void testConstructor_MessageProvided_CreatesInstanceWithMessage() {
        // Arrange
        String errorMessage = "Resource not found.";

        // Act
        ResourceNotFound exception = new ResourceNotFound(errorMessage);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
        //assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
