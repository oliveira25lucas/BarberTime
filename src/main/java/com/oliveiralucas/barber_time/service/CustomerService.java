package com.oliveiralucas.barber_time.service;

import com.oliveiralucas.barber_time.data.dto.CustomerDTO;
import com.oliveiralucas.barber_time.data.dto.summary.CustomerSummaryDTO;
import com.oliveiralucas.barber_time.enums.StatusEnum;
import com.oliveiralucas.barber_time.exception.NotFoundException;
import com.oliveiralucas.barber_time.mapper.CustomerMapper;
import com.oliveiralucas.barber_time.model.Customer;
import com.oliveiralucas.barber_time.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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
    public List<CustomerSummaryDTO> findAll() {
        Comparator<CustomerSummaryDTO> byRatingAverage = Comparator.comparing(
                CustomerSummaryDTO::getRatingAverage,
                Comparator.nullsLast(java.math.BigDecimal::compareTo)
        );
        Comparator<CustomerSummaryDTO> byRatingCount = Comparator.comparing(
                CustomerSummaryDTO::getRatingCount,
                Comparator.nullsLast(Integer::compare)
        );

        return customerMapper.toSummary(customerRepository.findAll())
                .stream()
                .filter(dto -> dto.getStatus() == StatusEnum.ACTIVE)
                .sorted(byRatingAverage.reversed().thenComparing(byRatingCount.reversed()))
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer id " + id + " not found"));
        return customerMapper.toDTO(entity);
    }

    public CustomerDTO create(CustomerDTO dto) {
        Customer entity = customerMapper.toEntity(dto);
        entity.setPassword(dto.getPassword());
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
