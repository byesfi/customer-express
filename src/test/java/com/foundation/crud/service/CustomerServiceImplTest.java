package com.foundation.crud.service;

import com.foundation.crud.exception.DuplicateResourceException;
import com.foundation.crud.exception.ResourceNotFoundException;
import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.CustomerDao;
import com.foundation.crud.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerServiceImpl Test")
class CustomerServiceImplTest {

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Captor
    private ArgumentCaptor<Customer> customerCaptor;

    @Test
    @DisplayName("Get Customer By ID - Valid ID")
    void testGetCustomerById_ValidId_ReturnsCustomer() {
        // Arrange
        Integer customerId = 1;
        Customer customer = new Customer(customerId, "John Doe", "john.doe@example.com", 25);
        when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.getCustomerById(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(25, result.getAge());
        verify(customerDao, times(1)).selectCustomerById(customerId);
    }

    @Test
    @DisplayName("Get Customer By ID - Invalid ID")
    void testGetCustomerById_InvalidId_ThrowsResourceNotFound() {
        // Arrange
        Integer customerId = 1;
        when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(customerId));
        verify(customerDao, times(1)).selectCustomerById(customerId);
    }

    @Test
    @DisplayName("Get All Customers")
    void testGetAllCustomers_ReturnsListOfCustomers() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "John Doe", "john.doe@example.com", 25));
        customers.add(new Customer(2, "Jane Smith", "jane.smith@example.com", 30));
        when(customerDao.selectAllCustomers()).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(customers, result);
        verify(customerDao, times(1)).selectAllCustomers();
    }

    @Test
    @DisplayName("Create customer: should insert customer when email is unique")
    void testCreateCustomer_EmailIsUnique_InsertsCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("john.doe@example.com");

        when(customerDao.existCustomerWithEmail(customer.getEmail())).thenReturn(false);

        // Act
        customerService.addCustomer(customer);

        // Assert
        verify(customerDao).insertCustomer(customer);
    }

    @Test
    @DisplayName("Create customer: should throw DuplicateResourceException when email already exists")
    void testCreateCustomer_EmailAlreadyExists_ThrowsDuplicateResourceException() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("john.doe@example.com");

        when(customerDao.existCustomerWithEmail(customer.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> customerService.addCustomer(customer));
    }

    @Test
    @DisplayName("Update Customer - Valid Customer")
    void updateCustomer_ValidCustomer_CallsUpdateCustomer() {
        // Arrange
        Integer customerId = 1;
        Customer newUpdatedCustomer = new Customer();
        newUpdatedCustomer.setAge(30);

        Customer existingCustomer = new Customer(customerId, "John Doe", "john.doe@example.com", 25);
        when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));


        // Act
        customerService.updateCustomer(customerId, newUpdatedCustomer);

        // Assert
        verify(customerDao, times(1)).selectCustomerById(customerId);
        verify(customerDao, times(1)).updateCustomer(customerCaptor.capture());

        // Verify captured customer attributes
        Customer capturedCustomer = customerCaptor.getValue();
        assertEquals("John Doe", capturedCustomer.getName());
        assertEquals("john.doe@example.com", capturedCustomer.getEmail());
        assertEquals(30, capturedCustomer.getAge());
    }

    @Test
    @DisplayName("Update Customer - Invalid Customer ID")
    void updateCustomer_InvalidCustomerId_ThrowsResourceNotFound() {
        // Arrange
        Integer customerId = 1;
        Customer updatedCustomer = new Customer(customerId, "Jane Smith", "jane@example.com", 25);

        when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(customerId, updatedCustomer));
        verify(customerDao, times(1)).selectCustomerById(customerId);
        verify(customerDao, never()).updateCustomer(any(Customer.class));

    }

    @Test
    @DisplayName("Delete Customer - Valid Customer ID")
    void deleteCustomer_ValidCustomerId_CallsDeleteCustomer() {
        // Arrange
        Integer customerId = 1;
        when(customerDao.existCustomerWithID(customerId)).thenReturn(true);
        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerDao, times(1)).deleteCustomer(customerId);
    }

    @Test
    @DisplayName("Delete Customer - Invalid Customer ID")
    void deleteCustomer_InvalidCustomerId_ThrowsResourceNotFound() {
        // Arrange
        Integer customerId = 1;
        doThrow(ResourceNotFoundException.class).when(customerDao).existCustomerWithID(customerId);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(customerId));
    }
}
