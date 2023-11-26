package com.foundation.crud.repository.impl;

import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.CustomerDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the CustomerDao interface that uses an in-memory list to store customer data.
 */
@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    public static final String THIS_METHOD_IS_NOT_IMPLEMENTED = "This method is not implemented.";
    private static List<Customer> customers;

    static{
        customers = new ArrayList<>();
        Customer bob = new Customer(1, "bob", "bob@email.com", 33);
        Customer alex = new Customer(2, "alex", "alex@email.com", 18);
        Customer joe = new Customer(3, "Joe", "joe@eamil.com", 22);
        Customer angela = new Customer(4, "angela", "angela@email.com", 21);

        customers.add(bob);
        customers.add(alex);
        customers.add(joe);
        customers.add(angela);
    }

    /**
     * Sets the list of customers used by the CustomerListDataAccessService.
     *
     * @param customers the list of customers to set
     */
    public static void setCustomers(List<Customer> customers) {
        CustomerListDataAccessService.customers = customers;
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return an Optional containing the customer if found, or an empty Optional if not found
     */
    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        return customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst();
    }

    /**
     * Retrieves all customers.
     *
     * @return a List containing all the customers
     */
    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public void insertCustomer(Customer customer) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    /**
     * Update existing customer object.
     */
    @Override
    public void updateCustomer(Customer customer) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    /**
     * Delete a customer from the system.
     *
     * @param customerId is the ID of the customer.
     */
    @Override
    public void deleteCustomer(Integer customerId) {
        throw new UnsupportedOperationException(THIS_METHOD_IS_NOT_IMPLEMENTED);
    }

    /**
     * Checks if a customer with the given email exists.
     *
     * @param email the email to check for existence
     * @return {@code true} if a customer with the email exists, {@code false} otherwise
     */
    @Override
    public boolean existCustomerWithEmail(String email) {
        throw new UnsupportedOperationException(THIS_METHOD_IS_NOT_IMPLEMENTED);
    }

    /**
     * Checks if a customer with the given ID exists.
     *
     * @param customerId the customer ID to check for existence
     * @return {@code true} if a customer with the ID exists, {@code false} otherwise
     */
    @Override
    public boolean existCustomerWithId(Integer customerId) {
        throw new UnsupportedOperationException(THIS_METHOD_IS_NOT_IMPLEMENTED);
    }
}
