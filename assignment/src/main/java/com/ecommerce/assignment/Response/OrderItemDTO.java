package com.ecommerce.assignment.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long orderItemId;
    private ItemDTO item;
    private Integer quantity;
    private double discount;
    private double orderedProductPrice;
}
