package com.foundation.crud.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DuplicateResourceExceptionTest {

    @Test
    @DisplayName("Constructor: should create instance with provided error message")
    void testConstructor_MessageProvided_CreatesInstanceWithMessage() {
        // Arrange
        String errorMessage = "Duplicate resource found.";

        // Act
        DuplicateResourceException exception = new DuplicateResourceException(errorMessage);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }
}