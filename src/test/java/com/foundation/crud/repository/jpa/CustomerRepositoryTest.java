package com.foundation.crud.repository.jpa;

import com.foundation.crud.AbstractTestcontainers;
import com.foundation.crud.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository ut;

    @Test
    @DisplayName("Exist customer with email: should return true when customer exists")
    void existsCustomerByEmail() {
        //Given
        String mail = "john.doe7@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);
        ut.save(customer);

        //When
        boolean exist = ut.existsCustomerByEmail(mail);

        //Then
        assertThat(exist).isTrue();
    }

    @Test
    void existsCustomerById() {
        //Given
        String mail = "john.doe1@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);
        ut.save(customer);
        Long id = ut.findAll()
                .stream()
                .filter(c -> mail.equals(c.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //When
        boolean exist = ut.existsCustomerById(id);

        //Then
        assertThat(exist).isTrue();
    }
}