package com.foundation.crud.controller;

import com.foundation.crud.dto.CustomerRegistrationRequest;
import com.foundation.crud.dto.CustomerUpdateRequest;
import com.foundation.crud.model.Customer;
import com.foundation.crud.service.CustomerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for handling customer-related API endpoints.
 * Exposes endpoints to retrieve customers information.
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructs a new instance of {@code CustomerController} with the specified {@code CustomerService}.
     *
     * @param customerService the customer service to be used for retrieving customer data.
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers.
     */
    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Retrieves a specific customer by ID.
     *
     * @param customerId the ID of the customer to retrieve.
     * @return the customer with the specified ID.
     */
    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    /**
     * Registers a new customer.
     *
     * @param customerRegistrationRequest the customerRegistrationRequest object containing the customer details to be registered
     */
    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        customerService.addCustomer(customerRegistrationRequest);
    }

    /**
     * Updates an existing customer with the provided customer information.
     *
     * @param customerId the ID of the customer to be updated.
     * @param customerUpdateRequest the customerUpdateRequest object containing the updated information.
     */
    @PatchMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }

    /**
     * Deletes a customer with the specified customer ID.
     *
     * @param customerId the ID of the customer to be deleted.
     */
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
    }
}
