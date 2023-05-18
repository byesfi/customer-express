package com.foundation.crud.repository.jpa;

import com.foundation.crud.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The CustomerRepository interface provides data access operations for the Customer entity using Spring Data JPA.
 * It extends the JpaRepository interface, which provides basic CRUD operations and querying capabilities.
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
