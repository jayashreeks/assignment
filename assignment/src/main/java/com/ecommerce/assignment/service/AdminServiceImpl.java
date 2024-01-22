package com.ecommerce.assignment.service;


import com.ecommerce.assignment.Response.OrderDTO;
import com.ecommerce.assignment.constants.Constants;
import com.ecommerce.assignment.dao.DiscountCodeDao;
import com.ecommerce.assignment.dao.OrderDao;
import com.ecommerce.assignment.dao.UserDao;
import com.ecommerce.assignment.exceptions.APIException;
import com.ecommerce.assignment.exceptions.InvalidDiscountCodeException;
import com.ecommerce.assignment.model.DiscountCode;
import com.ecommerce.assignment.model.Order;
import com.ecommerce.assignment.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{


    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Autowired
    DiscountCodeDao discountCodeDao;

    public void generateDiscountCode(Long userId){

        User user=userDao.findUserById(userId);
        Long orderCount= Long.valueOf(user.getOrders().size());
        if(orderCount % Constants.N==0){
            DiscountCode dc=new DiscountCode();
            dc.setActive(Boolean.TRUE);
            dc.setUser(user);
            discountCodeDao.save(dc);
        }
        else
            throw new InvalidDiscountCodeException();

    }



}
