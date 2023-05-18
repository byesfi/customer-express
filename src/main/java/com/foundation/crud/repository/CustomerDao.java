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
    Optional<Customer> selectCustomerById(Integer customerId);

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    List<Customer> selectAllCustomers();

    /**
     * Insert a customer
     * @param customer the customer object to be inserted
     */
    void insertCustomer(Customer customer);

    /**
     * Update an existing customer object.
     * @param customer new customer information to be updated.
     */
    public void updateCustomer(Customer customer);

    /**
     * Delete a customer from the system.
     * @param customerId is the ID of the customer.
     */
    public void deleteCustomer(Integer customerId);
}
