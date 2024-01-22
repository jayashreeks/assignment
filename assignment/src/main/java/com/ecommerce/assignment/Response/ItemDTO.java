package com.ecommerce.assignment.Response;

import lombok.Data;

@Data
public class ItemDTO {

    private Long itemId;

    private String itemName;

    private String itemDescription;

    private Double itemPrice;
}
