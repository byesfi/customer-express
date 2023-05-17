package com.foundation.crud.service;

import com.foundation.crud.model.Customer;

import java.util.List;

/**
 * The CustomerService interface provides operations for retrieving customer information.
 */
public interface CustomerService {

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return the customer with the specified ID, or null if no customer is found
     */
    Customer getCustomerById(Integer customerId);


    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    List<Customer> getAllCustomers();
}
