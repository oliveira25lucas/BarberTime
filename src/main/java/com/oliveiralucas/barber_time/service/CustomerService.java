package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.CustomerDTO;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.CustomerMapper;
import com.oliveiralucas.barber_time.model.Customer;
import com.oliveiralucas.barber_time.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> findAll() {
        return customerMapper.toDTO(customerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer id " + id + " not found"));
        return customerMapper.toDTO(entity);
    }

    public CustomerDTO create(CustomerDTO dto) {
        Customer entity = customerMapper.toEntity(dto);
        entity = customerRepository.save(entity);
        return customerMapper.toDTO(entity);
    }

    public CustomerDTO update(Long id, CustomerDTO dto) {
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer id " + id + " not found"));
        customerMapper.updateFromDto(dto, entity);
        entity = customerRepository.save(entity);
        return customerMapper.toDTO(entity);
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException("Customer id " + id + " not found");
        }
        customerRepository.deleteById(id);
    }
}
