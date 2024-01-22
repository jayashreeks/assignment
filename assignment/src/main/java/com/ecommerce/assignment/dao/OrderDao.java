package com.ecommerce.assignment.dao;

import com.ecommerce.assignment.model.Cart;
import com.ecommerce.assignment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order,Long> {

}
