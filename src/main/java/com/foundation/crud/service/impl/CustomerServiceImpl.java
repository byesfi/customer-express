package com.foundation.crud.service.impl;

import com.foundation.crud.exception.ResourceNotFound;
import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.CustomerDao;
import com.foundation.crud.service.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFound("Customer with id [%s] not found.".formatted(customerId)));
    }


    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }


    /**
     * Creates a new customer.
     * @param customer the customer to be created
     */
    @Override
    public void createCustomer(Customer customer) {
        customerDao.insertCustomer(customer);
    }

    /**
     * Update an existing customer with new information.
     *
     * @param customer new customer information to be updated.
     */
    @Transactional
    @Override
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    /**
     * Delete a customer from system.
     *
     * @param customerId the ID of the customer to delete.
     */
    @Override
    public void deleteCustomer(Integer customerId) {
        customerDao.deleteCustomer(customerId);
    }
}
