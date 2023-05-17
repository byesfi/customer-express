package com.foundation.crud.service;

import com.foundation.crud.exception.ResourceNotFound;
import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.CustomerDao;
import com.foundation.crud.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testGetCustomerById_ValidId_ReturnsCustomer() {
        // Arrange
        Integer customerId = 1;
        Customer customer = new Customer(customerId, "John Doe", "john.doe@example.com", 25);
        when(customerDao.getCustomerById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.getCustomerById(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(25, result.getAge());
        verify(customerDao, times(1)).getCustomerById(customerId);
    }

    @Test
    void testGetCustomerById_InvalidId_ThrowsResourceNotFound() {
        // Arrange
        Integer customerId = 1;
        when(customerDao.getCustomerById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFound.class, () -> customerService.getCustomerById(customerId));
        verify(customerDao, times(1)).getCustomerById(customerId);
    }

    @Test
    void testGetAllCustomers_ReturnsListOfCustomers() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "John Doe", "john.doe@example.com", 25));
        customers.add(new Customer(2, "Jane Smith", "jane.smith@example.com", 30));
        when(customerDao.getAllCustomers()).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(customers, result);
        verify(customerDao, times(1)).getAllCustomers();
    }
}
