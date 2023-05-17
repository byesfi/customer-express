package com.foundation.crud.repository;

import com.foundation.crud.model.Customer;

import java.util.List;
import java.util.Optional;


/**
 * The CustomerDao interface provides data access operations for retrieving customer information.
 */
public interface CustomerDao {

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return an Optional containing the customer with the specified ID, or an empty Optional if no customer is found
     */
    Optional<Customer> getCustomerById(Integer customerId);

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    List<Customer> getAllCustomers();
}
