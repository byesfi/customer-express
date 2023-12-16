package com.foundation.crud.repository.impl;

import com.foundation.crud.mapper.CustomerRowMapper;
import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.CustomerDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJdbcDataAccessRepository implements CustomerDao {

    private static final String INSERT_CUSTOMER = "INSERT INTO customer(name, email, age) VALUES (?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE customer SET name = ?, email = ?, age = ? WHERE id = ?";
    private static final String SELECT_ALL_CUSTOMERS = "SELECT c.id, c.name, c.email, c.age FROM customer c";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT c.id, c.name, c.email, c.age FROM customer c WHERE c.id = ?";
    private static final String DELETE_FROM_CUSTOMER_WHERE_ID = "DELETE FROM customer c WHERE c.id = ?";
    private static final String COUNT_FROM_CUSTOMER_WHERE_ID = "SELECT COUNT(*) FROM customer c WHERE c.id = ?";
    private static final String COUNT_FROM_CUSTOMER_WHERE_EMAIL = "SELECT COUNT(*) FROM customer c WHERE c.email = ?";
    private JdbcTemplate jdbcTemplate;
    private CustomerRowMapper customerRowMapper;

    public CustomerJdbcDataAccessRepository(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return an Optional containing the customer with the specified ID, or an empty Optional if no customer is found
     */
    @Override
    public Optional<Customer> selectCustomerById(Long customerId) {
        return jdbcTemplate.query(SELECT_CUSTOMER_BY_ID, customerRowMapper, customerId).stream().findFirst();
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    @Override
    public List<Customer> selectAllCustomers() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, customerRowMapper);
    }

    /**
     * Insert a customer
     *
     * @param customer the customer object to be inserted
     */
    @Override
    public void insertCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER, customer.getName(), customer.getEmail(), customer.getAge());
    }

    /**
     * Update an existing customer object.
     *
     * @param customer new customer information to be updated.
     */
    @Override
    public void updateCustomer(Customer customer) {
        jdbcTemplate.update(UPDATE_CUSTOMER, customer.getName(), customer.getEmail(), customer.getAge(), customer.getId());
    }

    /**
     * Delete a customer from the system.
     *
     * @param customerId is the ID of the customer.
     */
    @Override
    public void deleteCustomer(Long customerId) {
        jdbcTemplate.update(DELETE_FROM_CUSTOMER_WHERE_ID, customerId);
    }

    /**
     * Checks if a customer with the given email exists.
     *
     * @param email the email to check for existence
     * @return {@code true} if a customer with the email exists, {@code false} otherwise
     */
    @Override
    public boolean existCustomerWithEmail(String email) {
        Integer count = jdbcTemplate.queryForObject(COUNT_FROM_CUSTOMER_WHERE_EMAIL, Integer.class, email);
        return count != null && count > 0;
    }

    /**
     * Checks if a customer with the given ID exists.
     *
     * @param customerId the customer ID to check for existence
     * @return {@code true} if a customer with the ID exists, {@code false} otherwise
     */
    @Override
    public boolean existCustomerWithId(Long customerId) {
        Integer count = jdbcTemplate.queryForObject(COUNT_FROM_CUSTOMER_WHERE_ID, Integer.class, customerId);
        return count != null && count > 0;
    }
}
