package com.foundation.crud.controller;

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
@RequestMapping("/api/v1/")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructs a new instance of {@code CustomerController} with the specified {@code CustomerService}.
     *
     * @param customerService the customer service to be used for retrieving customer data.
     */
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers.
     */
    @GetMapping("customers")
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    /**
     * Retrieves a specific customer by ID.
     *
     * @param customerId the ID of the customer to retrieve.
     * @return the customer with the specified ID.
     */
    @GetMapping("customers/{customerId}")
    public Customer getCustomer(@PathVariable Integer customerId){
        return customerService.getCustomerById(customerId);
    }

    /**
     * Create a new customer.
     */
    @PostMapping("customers")
    public void createCustomer(@RequestBody Customer customer){
        customerService.createCustomer(customer);
    }

    @PatchMapping("customers")
    public void updateCustomer(@RequestBody Customer customer){
        customerService.updateCustomer(customer);
    }

    /**
     * Delete a customer.
     */
    @DeleteMapping("customers/{customerId}")
    public void deleteCustomer(@PathVariable Integer customerId){
        customerService.deleteCustomer(customerId);
    }
}
