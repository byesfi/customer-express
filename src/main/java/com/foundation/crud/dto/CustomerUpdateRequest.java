package com.foundation.crud.dto;

/**
 * Represents a customer update request.
 * This class is a record that holds the name, email, and age of a customer.
 */
public record CustomerUpdateRequest(
        String name,

        String email,

        Integer age
) {
}