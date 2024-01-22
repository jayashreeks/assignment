package com.ecommerce.assignment.Response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {

    private Long cartId;
    private Double totalPrice = 0.0;
    private List<ItemDTO> items = new ArrayList<>();
}
