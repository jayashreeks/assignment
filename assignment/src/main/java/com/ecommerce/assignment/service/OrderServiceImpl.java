package com.ecommerce.assignment.service;

import com.ecommerce.assignment.Response.OrderDTO;
import com.ecommerce.assignment.Response.OrderItemDTO;
import com.ecommerce.assignment.constants.Constants;
import com.ecommerce.assignment.dao.*;
import com.ecommerce.assignment.exceptions.APIException;
import com.ecommerce.assignment.exceptions.InvalidDiscountCodeException;
import com.ecommerce.assignment.exceptions.ResourceNotFoundException;
import com.ecommerce.assignment.model.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    @Autowired
    CartDao cartDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Autowired
    DiscountCodeDao discountCodeDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    public boolean checkDiscountCodeValidity(Long discountCode, Long userId) {
        DiscountCode dc = discountCodeDao.getDiscountCodeDetails(discountCode, userId);
        User user=userDao.findUserById(userId);
        boolean isvalid = false;
        if (!ObjectUtils.isEmpty(user)){
            if ( user.getOrders().size() % Constants.N == 0 && dc.isActive())
            isvalid = true;
            else if (user.getOrders().size() % Constants.N > 0)
                throw new InvalidDiscountCodeException(discountCode, (long) user.getOrders().size());
            else if (dc.isActive())
                throw new InvalidDiscountCodeException(discountCode);
            else
                isvalid = false;
        }
        return isvalid;
    }

    public OrderDTO placeOrder(Long cartId,Long discountCode) throws InvalidDiscountCodeException{
        Cart cart = cartDao.findCartByCartId(cartId);

        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }

        Long orderPlacedByUserId=cart.getUser().getId();

        Long totalOrderCount = Long.valueOf(cart.getUser().getOrders().size());
        Order order = new Order();

        order.setOrderDate(Date.valueOf(LocalDate.now()));
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderCount(totalOrderCount+1);
        order.setUser(cart.getUser());
        boolean valid= checkDiscountCodeValidity(discountCode,orderPlacedByUserId);

        if(valid) {
            order.setTotalAmount((cart.getTotalPrice() - cart.getTotalPrice() * 0.1));
            order.setTotalDiscountAmount(cart.getTotalPrice() * 0.1);
            order.setDiscountCode(discountCode);
        }
        else {
            order.setTotalAmount((cart.getTotalPrice()));
            order.setTotalDiscountAmount((double) 0);
        }


        discountCodeDao.disableDiscountCode(discountCode);

        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems.size() == 0) {
            throw new APIException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();

            orderItem.setItem(cartItem.getItem());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrderedItemPrice(cartItem.getItemPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        orderItems = orderItemDao.saveAll(orderItems);

        order.setOrderItemList(orderItems);

        Order savedOrder = orderDao.save(order);

        cart.getCartItems().forEach(cartItem -> {
            Long quantity = cartItem.getQuantity();

            Item item = cartItem.getItem();


            cartService.deleteItemFromCart(cartId, cartItem.getItem().getItemId());

            item.setQuantity(item.getQuantity() - quantity);
        });

        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);

        orderItems.forEach(item -> orderDTO.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class)));

        return orderDTO;
    }

    public List<OrderDTO> getAllOrders() {

        List<Order> orders =orderDao.findAll();


        List<OrderDTO> orderDTOs=new ArrayList<>();
        List<OrderItemDTO> orderItemDTO=new ArrayList<>();
        OrderDTO orderDTO;

        for(Order order : orders) {
            orderItemDTO=order.getOrderItemList().stream().map(orderItem -> modelMapper.map(orderItem,OrderItemDTO.class)).collect(Collectors.toList());
            orderDTO=modelMapper.map(order, OrderDTO.class);
            orderDTO.setOrderItems(orderItemDTO);
            orderDTOs.add(orderDTO);

        }

        if (orderDTOs.size() == 0) {
            throw new APIException("No orders placed yet by the users");
        }

        return orderDTOs;
    }
}
