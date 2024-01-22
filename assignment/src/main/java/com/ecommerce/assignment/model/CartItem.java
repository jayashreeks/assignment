package com.ecommerce.assignment.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    private Long quantity;
    private Double discount;
    private Double itemPrice;

    @Override
    public String toString() {
        return "CartItem [cartItemId=" + cartItemId + ", quantity=" + quantity
                + ", discount=" + discount
                + ", itemPrice=" + itemPrice
                + ", item=" + item +"]";
    }

}
