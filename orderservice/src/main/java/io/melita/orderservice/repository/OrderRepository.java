package io.melita.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.melita.orderservice.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
        
}
