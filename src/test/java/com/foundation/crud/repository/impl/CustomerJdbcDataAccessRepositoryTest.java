package com.foundation.crud.repository.impl;

import com.foundation.crud.AbstractTestcontainers;
import com.foundation.crud.mapper.CustomerRowMapper;
import com.foundation.crud.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("CustomerJdbcDataAccessService Test")
class CustomerJdbcDataAccessRepositoryTest extends AbstractTestcontainers {

    private CustomerRowMapper customerRowMapper = new CustomerRowMapper();
    private JdbcTemplate jdbcTemplate;
    private CustomerJdbcDataAccessRepository ut;

    @BeforeEach
    void setUp() {
        ut = new CustomerJdbcDataAccessRepository(getJdbcTemplate(), customerRowMapper);
    }

    @Test
    @DisplayName("Get Customer By ID - Customer Found")
    void selectCustomerById_CustomerFound() {
        //Given
        String mail = "john.doe1@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);
        ut.insertCustomer(customer);
        Long id = ut.selectAllCustomers()
                .stream()
                .filter(c -> mail.equals(c.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //When
        Optional<Customer> actual = ut.selectCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(name);
            assertThat(c.getEmail()).isEqualTo(mail);
            assertThat(c.getAge()).isEqualTo(age);
        });
    }

    @Test
    @DisplayName("Get Customer By ID - Customer Not Found")
    void selectCustomerById_CustomerNotFound() {
        //Given
        Long id = -1L;
        //When
        Optional<Customer> actual = ut.selectCustomerById(id);

        //Then
        assertThat(actual).isNotPresent();
    }

    @Test
    void selectAllCustomers() {
        //Given
        String mail = "john.doe2@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);
        ut.insertCustomer(customer);

        //When
        List<Customer> actual = ut.selectAllCustomers();

        //Then
        assertThat(actual).isNotNull();
    }

    @Test
    void insertCustomer() {
        //Given
        String mail = "john.doe3@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);

        //When
        ut.insertCustomer(customer);
        Optional<Customer> actual = ut.selectAllCustomers()
                .stream()
                .filter(c -> mail.equals(c.getEmail()))
                .findFirst();

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getName()).isEqualTo(name);
            assertThat(c.getEmail()).isEqualTo(mail);
            assertThat(c.getAge()).isEqualTo(age);
        });
    }

    @Test
    void insertAlreadyExistCustomer() {
        //Given
        String mail = "john.doe4@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);

        //When&Then
        ut.insertCustomer(customer);
        DuplicateKeyException duplicateKeyException = Assertions.assertThrows(DuplicateKeyException.class, () -> ut.insertCustomer(customer));
        assertThat(duplicateKeyException.getMessage()).contains("ERROR: duplicate key value violates unique constraint \"customer_email_unique\"");
    }

    @Test
    @DisplayName("Update Customer - Valid Customer")
    void updateCustomer() {
        //Given
        String mail = "john.doe5@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);
        ut.insertCustomer(customer);
        Customer insertedCustomer = ut.selectAllCustomers()
                .stream()
                .filter(c -> mail.equals(c.getEmail()))
                .findFirst()
                .orElseThrow();
        int newAge = 34;
        String newName = "John Doe Test";
        String newMail = "john.doetest@mail.com";
        insertedCustomer.setName(newName);
        insertedCustomer.setEmail(newMail);
        insertedCustomer.setAge(newAge);

        //When
        ut.updateCustomer(insertedCustomer);
        Optional<Customer> actual = ut.selectAllCustomers()
                .stream()
                .filter(c -> newMail.equals(c.getEmail()))
                .findFirst();

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getName()).isEqualTo(newName);
            assertThat(c.getEmail()).isEqualTo(newMail);
            assertThat(c.getAge()).isEqualTo(newAge);
        });
    }

    @Test
    @DisplayName("Delete Customer - Valid Customer ID")
    void deleteCustomer() {
        //Given
        String mail = "john.doe6@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);
        ut.insertCustomer(customer);
        Long id = ut.selectAllCustomers()
                .stream()
                .filter(c -> mail.equals(c.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        //When
        ut.deleteCustomer(id);
        Optional<Customer> actual = ut.selectAllCustomers()
                .stream()
                .filter(c -> mail.equals(c.getEmail()))
                .findFirst();

        //Then
        assertThat(actual).isNotPresent();
    }

    @Test
    @DisplayName("Exist customer with email: should return true when customer exists")
    void testExistCustomerWithEmail_CustomerExists_ReturnsTrue() {
        //Given
        String mail = "john.doe20@email.com";

        //When
        boolean exist = ut.existCustomerWithEmail(mail);

        //Then
        assertThat(exist).isFalse();
    }

    @Test
    @DisplayName("Exist customer with email: should return true when customer exists")
    void testExistCustomerWithEmail_CustomerDoesNotExist_ReturnsFalse() {
        //Given
        String mail = "john.doe7@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);
        ut.insertCustomer(customer);

        //When
        boolean exist = ut.existCustomerWithEmail(mail);

        //Then
        assertThat(exist).isTrue();
    }

    @Test
    void existCustomerWithId() {
        //Given
        String mail = "john.doe8@email.com";
        String name = "John Doe";
        int age = 33;
        Customer customer = new Customer(name, mail, age);
        ut.insertCustomer(customer);
        Long id = ut.selectAllCustomers()
                .stream()
                .filter(c -> mail.equals(c.getEmail()))
                .findFirst()
                .map(Customer::getId)
                .orElseThrow();

        //When
        boolean exist = ut.existCustomerWithId(id);

        //Then
        assertThat(exist).isTrue();
    }
}