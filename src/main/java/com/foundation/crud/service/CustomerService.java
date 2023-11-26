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
     * @param customerId the ID of the customer to retrieve.
     * @return the customer with the specified ID, or null if no customer is found.
     */
    Customer getCustomerById(Integer customerId);


    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    List<Customer> getAllCustomers();

    /**
     * Add a customer into system.
     * @param customer is the customer object to inserted.
     */
    void addCustomer(Customer customer);

    /**
     * Update a customer with new information.
     * @param customer new customer information to be updated.
     */
    public void updateCustomer(Integer customerId, Customer customer);

    /**
     * Delete a customer from system.
     * @param customerId the ID of the customer to delete.
     */
    public void deleteCustomer(Integer customerId);
}
