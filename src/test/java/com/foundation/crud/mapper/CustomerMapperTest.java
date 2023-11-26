package com.foundation.crud.mapper;

import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.dto.CustomerUpdateRequest;
import com.foundation.crud.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerMapperTest {
    @Test
    @DisplayName("map customer registration request to customer entity")
    void testMappingCustomerRegistrationRequestToCustomerEntity() {
        // Arrange
        CustomerRegistrationRequest registrationRequest = new CustomerRegistrationRequest("John Doe", "john.doe@example.com", 25);

        // Act
        Customer customer = CustomerMapper.INSTANCE.toCustomer(registrationRequest);

        // Assert
        assertNull(customer.getId()); // id should be ignored
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals(25, customer.getAge());
    }

    @Test
    @DisplayName("map customer update request to customer entity")
    void testMappingCustomerUpdateRequestToCustomerEntity() {
        // Arrange
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("John Doe", "john.doe@example.com", 25);

        // Act
        Customer customer = CustomerMapper.INSTANCE.toCustomer(updateRequest);

        // Assert
        assertNull(customer.getId()); // id should be ignored
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals(25, customer.getAge());
    }
}
