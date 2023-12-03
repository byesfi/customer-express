package com.foundation.crud.service;

import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.dto.CustomerUpdateRequest;
import com.foundation.crud.exception.DuplicateResourceException;
import com.foundation.crud.exception.RequestValidationException;
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

        // Mock dependencies
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

        // Mock dependencies
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

        // Mock dependencies
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
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest("John Doe", "john.doe@example.com", 25 );

        // Mock dependencies
        when(customerDao.existCustomerWithEmail(customerRegistrationRequest.email())).thenReturn(false);

        // Act
        customerService.addCustomer(customerRegistrationRequest);

        // Assert
        verify(customerDao).insertCustomer(any(Customer.class));
    }

    @Test
    @DisplayName("Create customer: should throw DuplicateResourceException when email already exists")
    void testCreateCustomer_EmailAlreadyExists_ThrowsDuplicateResourceException() {
        // Arrange
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest("John Doe", "john.doe@example.com", 25 );

        // Mock dependencies
        when(customerDao.existCustomerWithEmail(customerRegistrationRequest.email())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> customerService.addCustomer(customerRegistrationRequest));
    }

    @Test
    @DisplayName("Update Customer - Valid Customer")
    void updateCustomer_ValidCustomer_CallsUpdateCustomer() {
        // Arrange
        Integer customerId = 1;
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("John Doe", "john.doe@example.com", 30);
        Customer existingCustomer = new Customer(customerId, "Old Name", "old.name@example.com", 25);

        // Mock dependencies
        when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerDao.existCustomerWithEmail(customerUpdateRequest.email())).thenReturn(false);


        // Act
        customerService.updateCustomer(customerId, customerUpdateRequest);

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
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("Jane Smith", "jane@example.com", 25);

        // Mock dependencies
        when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(customerId, customerUpdateRequest));
        verify(customerDao, times(1)).selectCustomerById(customerId);
        verify(customerDao, never()).updateCustomer(any(Customer.class));

    }

    @Test
    @DisplayName("Update Customer - Already Existing Customer Email")
    void testUpdateCustomer_WithExistingEmail() {
        // Arrange
        Integer customerId = 1;
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("New Name", "existing.email@example.com", 30);
        Customer existingCustomer = new Customer(customerId, "Old Name", "old.email@example.com", 25);

        // Mock dependencies
        when(customerDao.existCustomerWithEmail(updateRequest.email())).thenReturn(true);
        when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));

        // Act and Assert
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> customerService.updateCustomer(customerId, updateRequest));
        assertEquals("email already taken", exception.getMessage());

        // Verify that customerDao.updateCustomer was not called
        verify(customerDao, never()).updateCustomer(existingCustomer);
    }

    @Test
    @DisplayName("Update Customer - No Changes")
    void testUpdateCustomer_NoChanges() {
        // Arrange
        Integer customerId = 1;
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null, -1); // No changes
        Customer existingCustomer = new Customer(customerId, "Old Name", "old.email@example.com", 25);

        // Mock dependencies
        when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(existingCustomer));

        // Act and Assert
        RequestValidationException exception = assertThrows(RequestValidationException.class,
                () -> customerService.updateCustomer(customerId, updateRequest));
        assertEquals("no data changes found", exception.getMessage());

        // Verify that customerDao.updateCustomer was not called
        verify(customerDao, never()).updateCustomer(existingCustomer);
    }

    @Test
    @DisplayName("Delete Customer - Valid Customer ID")
    void deleteCustomer_ValidCustomerId_CallsDeleteCustomer() {
        // Arrange
        Long customerId = 1L;
        when(customerDao.existCustomerWithId(customerId)).thenReturn(true);

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerDao, times(1)).deleteCustomer(customerId);
    }

    @Test
    @DisplayName("Delete Customer - Invalid Customer ID")
    void deleteCustomer_InvalidCustomerId_ThrowsResourceNotFound() {
        // Arrange
        Long customerId = 1L;
        doThrow(ResourceNotFoundException.class).when(customerDao).existCustomerWithId(customerId);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(customerId));
    }
}
