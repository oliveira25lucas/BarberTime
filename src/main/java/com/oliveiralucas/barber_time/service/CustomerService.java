package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.model.Customer;
import com.oliveiralucas.barber_time.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer create(Customer customer) {
        customer.setId(null);
        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Customer " + id + " not found"));
    }

    public Customer update(Long id, Customer customer) {
        Customer existingCustomer = findById(id);
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setAddress(customer.getAddress());
        return customerRepository.save(existingCustomer);
    }

    public void delete(Long id) {
        Customer customer = findById(id);
        customerRepository.deleteById(id);
    }

}
