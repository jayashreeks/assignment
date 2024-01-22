package com.ecommerce.assignment.dao;

import com.ecommerce.assignment.model.DiscountCode;
import com.ecommerce.assignment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCodeDao extends JpaRepository<DiscountCode, Long> {

    @Query("SELECT dc FROM DiscountCode dc JOIN dc.user u  WHERE dc.id = ?1 AND u.id = ?2")
    DiscountCode getDiscountCodeDetails(Long discountCode, Long userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DiscountCode set isActive=false WHERE id = ?1")
    void disableDiscountCode(Long couponId);
}
