package com.foundation.crud.dto;

/**
 * Represents a customer registration request.
 * This class is a record that holds the name, email, and age of a customer.
 */
public record CustomerRegistrationRequest(
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
