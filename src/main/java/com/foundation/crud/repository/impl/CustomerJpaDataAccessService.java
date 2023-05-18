package com.foundation.crud.repository.impl;

import com.foundation.crud.exception.ResourceNotFound;
import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.CustomerDao;
import com.foundation.crud.repository.jpa.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link CustomerDao} interface using Spring Data JPA.
 * Provides data access methods to retrieve customer information from a database.
 */
@Repository("jpa")
public class CustomerJpaDataAccessService implements CustomerDao {

    private final CustomerRepository customerRepository;

    /**
     * Constructs a new CustomerJpaDataAccessService with the provided CustomerRepository.
     *
     * @param customerRepository the repository for customer data access
     */
    public CustomerJpaDataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     *  Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return an Optional containing the customer, or an empty Optional if not found
     */
    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * @param customer the customer object to be inserted
     */
    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    /**
     * Update existing customer object.
     *
     * @param customer new customer information to be updated.
     */
    @Transactional
    @Override
    public void updateCustomer(Customer customer) {
        Customer existingCustomer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new ResourceNotFound("The customer id [%s] is not found.".formatted(customer.getId())));

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAge(customer.getAge());

        customerRepository.save(existingCustomer);
    }

    /**
     * Delete a customer from the system.
     * @param customerId is the ID of the customer.
     */
    @Override
    public void deleteCustomer(Integer customerId){
        customerRepository.deleteById(customerId);
    }
}