package com.foundation.crud.controller;

import com.foundation.crud.model.Customer;
import com.foundation.crud.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTestIT {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCustomers_ReturnsListOfCustomers() throws Exception {
        // Arrange
        Customer customer1 = new Customer(1, "John Doe", "john@example.com", 30);
        Customer customer2 = new Customer(2, "Jane Smith", "jane@example.com", 25);
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    void testGetCustomer_ValidId_ReturnsCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer(1, "John Doe", "john@example.com", 30);

        when(customerService.getCustomerById(anyInt())).thenReturn(customer);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/{customerId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }
}
