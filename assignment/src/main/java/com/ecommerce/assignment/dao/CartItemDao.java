package com.ecommerce.assignment.dao;

import com.ecommerce.assignment.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.item.id = ?2")
    CartItem findCartItemByItemIdAndCartId(Long cartId, Long itemId);


    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.item.id = ?2")
    void deleteCartItemByItemIdAndCartId( Long cartId,Long itemId);
}
