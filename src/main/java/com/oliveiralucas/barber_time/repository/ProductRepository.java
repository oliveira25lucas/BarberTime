package com.oliveiralucas.barber_time.repository;

import com.oliveiralucas.barber_time.model.BarberService;
import com.oliveiralucas.barber_time.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
