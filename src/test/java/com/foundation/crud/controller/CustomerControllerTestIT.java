package com.foundation.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.dto.CustomerUpdateRequest;
import com.foundation.crud.model.Customer;
import com.foundation.crud.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@DisplayName("CustomerController Integration Tests")
class CustomerControllerTestIT {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get all customers - Returns list of customers")
    void testGetCustomers_ReturnsListOfCustomers() throws Exception {
        // Arrange
        Customer customer1 = new Customer(1L, "John Doe", "john@example.com", 30);
        Customer customer2 = new Customer(2L, "Jane Smith", "jane@example.com", 25);
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    @DisplayName("Get customer by ID - Returns customer")
    void testGetCustomer_ValidId_ReturnsCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer(1L, "John Doe", "john@example.com", 30);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/{customerId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @DisplayName("Create customer - Returns successful response")
    void testCreateCustomer() throws Exception {
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest("John Doe", "john.doe@email.com", 25);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerRegistrationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(customerService, times(1)).addCustomer(any(CustomerRegistrationRequest.class));
    }

    @Test
    @DisplayName("Update customer - Returns successful response")
    void testUpdateCustomer() throws Exception {
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("John Doe", "john.doe@email.com", 25);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(customerService, times(1)).updateCustomer(anyLong(), any(CustomerUpdateRequest.class));
    }

    @Test
    @DisplayName("Delete customer - Returns successful response")
    void testDeleteCustomer() throws Exception {
        Long customerId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/customers/{customerId}", customerId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(customerService, times(1)).deleteCustomer(customerId);
    }

    // Utility method to convert object to JSON string
    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
