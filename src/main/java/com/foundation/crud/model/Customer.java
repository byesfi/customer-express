package com.foundation.crud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Objects;

/**
 * The Customer class represents a customer entity in the application.
 */
@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "customer_email_unique",
                        columnNames = "email"
                )
        }
)
public class  Customer {

    @Id
    @SequenceGenerator(
            name = "customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"
    )
    private Long id;

    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private String email;

    @Column(
            nullable = false
    )
    private Integer age;

    /**
     * Default constructor for the Customer class.
     */
    public Customer() {
    }

    /**
     * Constructs a Customer object with the specified name, email, and age.
     *
     * @param name  the name of the customer
     * @param email the email of the customer
     * @param age   the age of the customer
     */
    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    /**
     * Constructs a Customer object with the specified id, name, email, and age.
     *
     * @param id    the id of the customer
     * @param name  the name of the customer
     * @param email the email of the customer
     * @param age   the age of the customer     * @param age   the age of the customer

     */
    public Customer(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    /**
     * Returns the id of the customer.
     *
     * @return the id of the customer
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id of the customer.
     *
     * @param id the id of the customer
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the customer.
     *
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name the name of the customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email of the customer.
     *
     * @return the email of the customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the customer.
     *
     * @param email the email of the customer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the age of the customer.
     *
     * @return the age of the customer
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the customer.
     *
     * @param age the age of the customer
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(getId(), customer.getId())
                && Objects.equals(getName(), customer.getName())
                && Objects.equals(getEmail(), customer.getEmail())
                && Objects.equals(getAge(), customer.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getAge());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
