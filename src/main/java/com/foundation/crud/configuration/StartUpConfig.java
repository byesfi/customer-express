package com.foundation.crud.configuration;

import com.foundation.crud.model.Customer;
import com.foundation.crud.repository.jpa.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StartUpConfig {

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
		return args -> {
				Customer bob = new Customer("bob", "bob@email.com", 33);
				Customer alex = new Customer("alex", "alex@email.com", 18);
				Customer joe = new Customer("Joe", "joe@eamil.com", 22);
				Customer angela = new Customer("angela", "angela@email.com", 21);
				List<Customer> customers = List.of(bob, alex, angela, joe);
				//customerRepository.saveAll(customers);
		};
	}
}
