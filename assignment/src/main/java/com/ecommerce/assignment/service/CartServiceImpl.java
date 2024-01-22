package com.ecommerce.assignment.service;

import com.ecommerce.assignment.Response.ItemDTO;
import com.ecommerce.assignment.dao.CartItemDao;
import com.ecommerce.assignment.dao.ItemDao;
import com.ecommerce.assignment.dao.OrderDao;
import com.ecommerce.assignment.exceptions.APIException;
import com.ecommerce.assignment.model.*;
import org.modelmapper.ModelMapper;
import com.ecommerce.assignment.Response.CartDTO;
import com.ecommerce.assignment.dao.CartDao;
import com.ecommerce.assignment.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService{
    @Autowired
    CartDao cartDao;

    @Autowired
    ItemDao  itemDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    CartItemDao cartItemDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addItemToCart(Long cartId, Long itemId, Long quantity){

        Cart cart = cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Item item = itemDao.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "itemId", itemId));

        CartItem cartItem = cartItemDao.findCartItemByItemIdAndCartId(cartId, itemId);

        if (cartItem != null) {
            throw new APIException("Item " + item.getItemName() + " already exists in the cart");
        }

        if (item.getQuantity() == 0) {
            throw new APIException(item.getItemName() + " is not available");
        }

        if (item.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + item.getItemName()
                    + " less than or equal to the quantity " + item.getQuantity() + ".");
        }

        CartItem newCartItem = new CartItem();

        newCartItem.setItem(item);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
//        newCartItem.setDiscount(item.getDiscount());
        newCartItem.setItemPrice(item.getItemPrice());

        cartItemDao.save(newCartItem);

        item.setQuantity(item.getQuantity() - quantity);

        cart.setTotalPrice(cart.getTotalPrice() + (item.getItemPrice() * quantity));
        List<CartItem> cartItemList = cart.getCartItems();
        cartItemList.add(newCartItem);
        cart.setCartItems(cartItemList);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ItemDTO> itemDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getItem(), ItemDTO.class)).collect(Collectors.toList());

        cartDTO.setItems(itemDTOs);

        return cartDTO;

    }

    @Override
    public CartDTO getCart(Long cartId){
        Cart cart = cartDao.findCartByCartId(cartId);

        if (cart == null) {
            throw new ResourceNotFoundException("Cart", "cartId", cartId);
        }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ItemDTO> items = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getItem(), ItemDTO.class)).collect(Collectors.toList());

        cartDTO.setItems(items);

        return cartDTO;
    }

    @Override
    public String deleteItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItem cartItem = cartItemDao.findCartItemByItemIdAndCartId(cartId, itemId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Item", "itemId", itemId);
        }

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getItemPrice() * cartItem.getQuantity()));

        Item item = cartItem.getItem();
        item.setQuantity(item.getQuantity() + cartItem.getQuantity());

        cartItemDao.deleteCartItemByItemIdAndCartId(cartId,itemId) ;

        return "Item " + cartItem.getItem().getItemName() + " removed from the cart !!!";
    }


}
