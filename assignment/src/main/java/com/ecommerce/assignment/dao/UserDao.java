package com.ecommerce.assignment.dao;

import com.ecommerce.assignment.model.Cart;
import com.ecommerce.assignment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findUserById(Long userId);
}
