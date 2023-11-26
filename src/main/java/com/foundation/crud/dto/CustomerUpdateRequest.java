package com.foundation.crud.dto;

/**
 * Represents a customer update request.
 * This class is a record that holds the name, email, and age of a customer.
 */
public record CustomerUpdateRequest(
        /**
         * The name of the customer.
         */
        String name,

        /**
         * The email of the customer.
         */
        String email,

        /**
         * The age of the customer.
         */
        Integer age
) {
}