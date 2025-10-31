package com.oliveiralucas.barber_time.repository;

import com.oliveiralucas.barber_time.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
