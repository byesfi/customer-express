package com.foundation.crud.repository;

import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.impl.CustomerListDataAccessService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerListDataAccessService Test")
class CustomerListDataAccessServiceTest {

    private CustomerDao customerDao;

    @Mock
    private List<Customer> customersMock;

    @BeforeEach
    void setUp() {
        customerDao = new CustomerListDataAccessService();
        CustomerListDataAccessService.setCustomers(customersMock);
    }

    @Test
    @DisplayName("Get Customer By ID - Customer Found")
    void testGetCustomerById_CustomerFound() {
        Customer expectedCustomer = new Customer(1, "Bob", "bob@email.com", 33);
        when(customersMock.stream()).thenReturn(Arrays.stream(new Customer[]{expectedCustomer}));

        Optional<Customer> result = customerDao.selectCustomerById(1);

        assertTrue(result.isPresent());
        assertEquals(expectedCustomer, result.get());
        verify(customersMock, times(1)).stream();
    }

    @Test
    @DisplayName("Get Customer By ID - Customer Not Found")
    void testGetCustomerById_CustomerNotFound() {
        when(customersMock.stream()).thenReturn(Arrays.stream(new Customer[]{}));

        Optional<Customer> result = customerDao.selectCustomerById(1);

        assertTrue(result.isEmpty());
        verify(customersMock, times(1)).stream();
    }

    @Test
    @DisplayName("Get All Customers")
    void testGetAllCustomers() {
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer(1, "Bob", "bob@email.com", 33),
                new Customer(2, "Alex", "alex@email.com", 18)
        );
        CustomerListDataAccessService.setCustomers(expectedCustomers);

        List<Customer> result = customerDao.selectAllCustomers();

        assertEquals(expectedCustomers.size(), result.size());
        assertEquals(expectedCustomers, result);
    }
}






