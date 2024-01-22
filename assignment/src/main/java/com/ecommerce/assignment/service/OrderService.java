package com.ecommerce.assignment.service;

import com.ecommerce.assignment.Response.OrderDTO;
import com.ecommerce.assignment.exceptions.InvalidDiscountCodeException;

import java.util.List;

public interface OrderService {

    OrderDTO placeOrder(Long cartId,Long discountCode) throws InvalidDiscountCodeException;

    boolean checkDiscountCodeValidity(Long discountCode, Long userId);

    List<OrderDTO> getAllOrders();
}
