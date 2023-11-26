package com.foundation.crud.service.impl;

import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.dto.CustomerUpdateRequest;
import com.foundation.crud.exception.DuplicateResourceException;
import com.foundation.crud.exception.RequestValidationException;
import com.foundation.crud.exception.ResourceNotFoundException;
import com.foundation.crud.mapper.CustomerMapper;
import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.CustomerDao;
import com.foundation.crud.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
     * @throws ResourceNotFoundException if no customer is found with the given ID
     */
    @Override
    public Customer getCustomerById(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id [%s] not found.".formatted(customerId)));
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
     * Add a new customer.
     *
     * @param registrationRequest the CustomerRegistrationRequest to be created.
     */
    @Override
    public void addCustomer(CustomerRegistrationRequest registrationRequest) {
        if(customerDao.existCustomerWithEmail(registrationRequest.email())){
           throw new DuplicateResourceException("Already exist a customer with email [%s]".formatted(registrationRequest.email()));
        }
        customerDao.insertCustomer(CustomerMapper.INSTANCE.toCustomer(registrationRequest));
    }

    /**
     * Update an existing customer with new information.
     *
     * @param customerId            customer id.
     * @param updateRequest new customer update request.
     */
    @Transactional
    @Override
    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest) {
        Customer existingCustomer = getCustomerById(customerId);

        boolean change = false;

        if (StringUtils.isNoneBlank(updateRequest.name()) && !existingCustomer.getName().equals(updateRequest.name())) {
            existingCustomer.setName(updateRequest.name());
            change = true;
        }

        if (StringUtils.isNoneBlank(updateRequest.email()) && !existingCustomer.getEmail().equals(updateRequest.email())) {
            if (customerDao.existCustomerWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            existingCustomer.setEmail(updateRequest.email());
            change = true;
        }

        if (!Objects.equals(existingCustomer.getAge(), updateRequest.age()) && updateRequest.age() >= 0) {
            existingCustomer.setAge(updateRequest.age());
            change = true;
        }

        if (!change) {
            throw new RequestValidationException("no data changes found");
        }
        customerDao.updateCustomer(existingCustomer);
    }

    /**
     * Delete a customer from system.
     *
     * @param customerId the ID of the customer to delete.
     */
    @Override
    public void deleteCustomer(Integer customerId) {
        if (!customerDao.existCustomerWithId(customerId)) {
            throw new ResourceNotFoundException("Customer with id [%s] is not found.".formatted(customerId));
        }
        customerDao.deleteCustomer(customerId);
    }
}
