package com.foundation.crud.controller;

import com.foundation.crud.model.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

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

    @GetMapping("customers")
    public List<Customer> getCustomers(){
        return customers;
    }

    @GetMapping("customers/{id}")
    public Customer getCustomer(@PathVariable Integer id){
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
