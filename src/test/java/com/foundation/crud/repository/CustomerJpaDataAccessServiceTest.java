package com.foundation.crud.repository;


import com.foundation.crud.exception.ResourceNotFoundException;
import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.impl.CustomerJpaDataAccessService;
import com.foundation.crud.repository.jpa.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerJpaDataAccessService Test")
class CustomerJpaDataAccessServiceTest {

    private CustomerDao customerDao;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerDao = new CustomerJpaDataAccessService(customerRepository);
    }

    @Test
    @DisplayName("Get Customer By ID - Customer Found")
    void testGetCustomerById_CustomerFound() {
        Customer expectedCustomer = new Customer(1, "Bob", "bob@email.com", 33);
        when(customerRepository.findById(1)).thenReturn(Optional.of(expectedCustomer));

        Optional<Customer> result = customerDao.selectCustomerById(1);

        assertTrue(result.isPresent());
        assertEquals(expectedCustomer, result.get());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Get Customer By ID - Customer Not Found")
    void testGetCustomerById_CustomerNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Customer> result = customerDao.selectCustomerById(1);

        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Get All Customers")
    void testGetAllCustomers() {
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer(1, "Bob", "bob@email.com", 33),
                new Customer(2, "Alex", "alex@email.com", 18)
        );
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        List<Customer> result = customerDao.selectAllCustomers();

        assertEquals(expectedCustomers.size(), result.size());
        assertEquals(expectedCustomers, result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update Customer - Valid Customer")
    void updateCustomer_ValidCustomer_UpdatesExistingCustomer() {
        // Arrange
        Integer customerId = 1;
        Customer existingCustomer = new Customer(customerId, "John Doe", "john@example.com", 30);
        Customer updatedCustomer = new Customer(customerId, "Jane Smith", "jane@example.com", 25);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        // Act
        customerDao.updateCustomer(updatedCustomer);

        // Assert
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    @DisplayName("Update Customer - Invalid Customer ID")
    void updateCustomer_InvalidCustomerId_ThrowsResourceNotFound() {
        // Arrange
        Integer customerId = 1;
        Customer updatedCustomer = new Customer(customerId, "Jane Smith", "jane@example.com", 25);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> customerDao.updateCustomer(updatedCustomer));
    }

    @Test
    @DisplayName("Delete Customer - Valid Customer ID")
    void deleteCustomer_ValidCustomerId_DeletesCustomer() {
        // Arrange
        Integer customerId = 1;

        // Act
        customerDao.deleteCustomer(customerId);

        // Assert
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    @DisplayName("Exist customer with email: should return true when customer exists")
    void testExistCustomerWithEmail_CustomerExists_ReturnsTrue() {
        // Arrange
        String existingEmail = "john.doe@example.com";
        when(customerRepository.existsCustomerByEmail(existingEmail)).thenReturn(true);

        // Act
        boolean result = customerDao.existCustomerWithEmail(existingEmail);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Exist customer with email: should return false when customer does not exist")
    void testExistCustomerWithEmail_CustomerDoesNotExist_ReturnsFalse() {
        // Arrange
        String nonExistingEmail = "jane.smith@example.com";
        when(customerRepository.existsCustomerByEmail(nonExistingEmail)).thenReturn(false);

        // Act
        boolean result = customerDao.existCustomerWithEmail(nonExistingEmail);

        // Assert
        assertFalse(result);
    }
}
