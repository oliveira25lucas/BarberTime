package com.oliveiralucas.barber_time.repository;

import com.oliveiralucas.barber_time.model.BarberService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberServiceRepository extends JpaRepository<BarberService, Long> {
}
