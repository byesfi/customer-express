package com.foundation.crud.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ResourceNotFound Test")
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Constructor with Message Provided")
    void testConstructor_MessageProvided_CreatesInstanceWithMessage() {
        // Arrange
        String errorMessage = "Resource not found.";

        // Act
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
        //assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
