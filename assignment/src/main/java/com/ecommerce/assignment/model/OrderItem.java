package com.ecommerce.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item  item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long quantity;
    private double orderedItemPrice;

    @Override
    public String toString() {
        return "OrderItem [orderItemId=" + orderItemId + ", item=" + item
                + ", order=" + order
                + ", quantity=" + quantity
                + ", orderedItemPrice=" + orderedItemPrice+"]";
    }

}
