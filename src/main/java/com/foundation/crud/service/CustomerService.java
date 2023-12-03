package com.foundation.crud.service;

import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.dto.CustomerUpdateRequest;
import com.foundation.crud.model.Customer;

import java.util.List;

/**
 * The CustomerService interface provides operations for retrieving customer information.
 */
public interface CustomerService {

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve.
     * @return the customer with the specified ID, or null if no customer is found.
     */
    Customer getCustomerById(Long customerId);


    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    List<Customer> getAllCustomers();

    /**
     * Add a customer into system.
     *
     * @param registrationRequest is the CustomerRegistrationRequest object to inserted
     */
    void addCustomer(CustomerRegistrationRequest registrationRequest);

    /**
     * Update a customer with new information.
     *
     * @param updateRequest new CustomerUpdateRequest information to be updated.
     */
    public void updateCustomer(Long customerId, CustomerUpdateRequest updateRequest);

    /**
     * Delete a customer from system.
     * @param customerId the ID of the customer to delete.
     */
    public void deleteCustomer(Long customerId);
}
