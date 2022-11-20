package com.daniel.delivery.repository;

import com.daniel.delivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getAllByPersonId(Long personId);
}
