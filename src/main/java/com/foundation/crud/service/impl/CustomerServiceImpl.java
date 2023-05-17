package com.foundation.crud.service.impl;

import com.foundation.crud.exception.ResourceNotFound;
import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.CustomerDao;
import com.foundation.crud.service.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CustomerService} interface.
 * Provides business logic for retrieving customer information.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    /**
     * Constructs a new CustomerServiceImpl with the specified CustomerDao implementation.
     *
     * @param customerDao the CustomerDao implementation to use
     */
    public CustomerServiceImpl(@Qualifier("jpa") CustomerDao customerDao){
        this.customerDao = customerDao;
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return the customer with the specified ID
     * @throws ResourceNotFound if no customer is found with the given ID
     */
    @Override
    public Customer getCustomerById(Integer customerId) {
        return customerDao.getCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFound("Customer with id [%s] not found.".formatted(customerId)));
    }


    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }
}
