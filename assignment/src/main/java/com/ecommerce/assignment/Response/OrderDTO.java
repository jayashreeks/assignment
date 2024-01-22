package com.ecommerce.assignment.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
    private Date orderDate;
    private Double totalAmount;
    private String orderStatus;
    private Long discountCode;
    private Double totalDiscountAmount;

}
