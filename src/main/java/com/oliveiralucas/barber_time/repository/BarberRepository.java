package com.oliveiralucas.barber_time.repository;

import com.oliveiralucas.barber_time.model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
}
