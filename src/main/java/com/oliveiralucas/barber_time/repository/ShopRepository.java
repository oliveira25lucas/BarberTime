package com.oliveiralucas.barber_time.repository;

import com.oliveiralucas.barber_time.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
