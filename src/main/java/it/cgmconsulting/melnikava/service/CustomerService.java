package it.cgmconsulting.melnikava.service;

import it.cgmconsulting.melnikava.entity.Customer;
import it.cgmconsulting.melnikava.repository.CustomerRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Optional<Customer> getCustomerByCustomerId(long customerId){
        return customerRepository.getCustomerByCustomerId(customerId);
    }
}
