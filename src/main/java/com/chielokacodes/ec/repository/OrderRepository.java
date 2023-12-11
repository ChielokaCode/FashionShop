package com.chielokacodes.ec.repository;

import com.chielokacodes.ec.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
