package com.ecommerce.assignment.service;

import com.ecommerce.assignment.Response.CartDTO;

public interface CartService {

    CartDTO addItemToCart(Long cartId, Long productId, Long quantity);
    CartDTO getCart(Long cartId);

    String deleteItemFromCart(Long cartId, Long itemId);
}
