package com.ecommerce.assignment.controller;


import com.ecommerce.assignment.Response.CartDTO;
import com.ecommerce.assignment.Response.OrderDTO;
import com.ecommerce.assignment.service.CartService;
import com.ecommerce.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/healthCheck",method = RequestMethod.GET)
    public String healthCheck() {

        return "Success";
    }

    @RequestMapping(value = "/carts/addItemToCart",method = RequestMethod.POST)
    public ResponseEntity<CartDTO> addItemToCart(@RequestParam("cartId") Long cartId, @RequestParam("itemId") Long itemId, @RequestParam("quantity") Long quantity) {
        CartDTO cartDTO=cartService.addItemToCart(cartId, itemId, quantity);

        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.CREATED);
    }


    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ResponseEntity<OrderDTO> placeOrder(@RequestParam("cartId") Long cartId,@RequestParam("discountCode") Long discountCode) {
        OrderDTO orderDTO=orderService.placeOrder(cartId,discountCode);

        return new ResponseEntity<OrderDTO>(orderDTO,HttpStatus.CREATED);
    }
}
