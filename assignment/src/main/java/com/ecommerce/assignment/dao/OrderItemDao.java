package com.ecommerce.assignment.dao;

import com.ecommerce.assignment.model.Order;
import com.ecommerce.assignment.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem,Long> {
}
