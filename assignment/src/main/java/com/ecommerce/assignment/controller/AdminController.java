package com.ecommerce.assignment.controller;

import com.ecommerce.assignment.Response.OrderDTO;
import com.ecommerce.assignment.model.CartItem;
import com.ecommerce.assignment.model.DiscountCode;
import com.ecommerce.assignment.service.AdminService;
import com.ecommerce.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/discountCode/generate",method = RequestMethod.POST)
    public String generateDiscountCode(@RequestParam("userId") Long userId) {
        adminService.generateDiscountCode(userId);

        return "Discount Created";
    }

    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    public Map<String, Object> getAllOrderDetails() {
        List<OrderDTO> orderDTOs=orderService.getAllOrders();

        Long totalOrderedItems= Long.valueOf(0);
        Double totalPurchaseAmount= Double.valueOf(0);
        Double totalDiscountAmount= Double.valueOf(0);
        List<Long> discountCodeList=new ArrayList<>();


        for (OrderDTO orderDTO : orderDTOs) {
            totalOrderedItems+=orderDTO.getOrderItems().size();
            totalPurchaseAmount+=orderDTO.getTotalAmount();
            totalDiscountAmount+=orderDTO.getTotalDiscountAmount();
            discountCodeList.add(orderDTO.getDiscountCode());
        }

        Map<String, Object> map= new HashMap<String, Object>();
        map.put("Number of items purchased",totalOrderedItems);
        map.put("Total purchase amount",totalPurchaseAmount);
        map.put("List of discount codes",discountCodeList.toArray());
        map.put("Total Discount Amount",totalDiscountAmount);
        return map;
    }
}
