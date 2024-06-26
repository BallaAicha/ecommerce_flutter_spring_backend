package org.etutoria.order.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {


    List<Order> findByCustomerId(String customerId);
}
