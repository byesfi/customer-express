package com.foundation.crud.repository;


import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.impl.CustomerJpaDataAccessService;
import com.foundation.crud.repository.jpa.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
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
class CustomerJpaDataAccessServiceTest {

    private CustomerDao customerDao;

    @Mock
    private CustomerRepository customerRepositoryMock;

    @BeforeEach
    void setUp() {
        customerDao = new CustomerJpaDataAccessService(customerRepositoryMock);
    }

    @Test
    void testGetCustomerById_CustomerFound() {
        Customer expectedCustomer = new Customer(1, "Bob", "bob@email.com", 33);
        when(customerRepositoryMock.findById(1)).thenReturn(Optional.of(expectedCustomer));

        Optional<Customer> result = customerDao.getCustomerById(1);

        assertTrue(result.isPresent());
        assertEquals(expectedCustomer, result.get());
        verify(customerRepositoryMock, times(1)).findById(1);
    }

    @Test
    void testGetCustomerById_CustomerNotFound() {
        when(customerRepositoryMock.findById(1)).thenReturn(Optional.empty());

        Optional<Customer> result = customerDao.getCustomerById(1);

        assertTrue(result.isEmpty());
        verify(customerRepositoryMock, times(1)).findById(1);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer(1, "Bob", "bob@email.com", 33),
                new Customer(2, "Alex", "alex@email.com", 18)
        );
        when(customerRepositoryMock.findAll()).thenReturn(expectedCustomers);

        List<Customer> result = customerDao.getAllCustomers();

        assertEquals(expectedCustomers.size(), result.size());
        assertEquals(expectedCustomers, result);
        verify(customerRepositoryMock, times(1)).findAll();
    }
}
