package com.ecommerce.assignment.dao;


import com.ecommerce.assignment.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c WHERE c.id = ?1")
    Cart findCartByCartId(Long cartId);
}
