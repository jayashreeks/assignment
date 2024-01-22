package com.ecommerce.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    private String itemName;

    private String itemDescription;

    private Double itemPrice;

    private Long quantity;

    @OneToMany(mappedBy = "item", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();

    @Override
    public String toString() {
        return "Item [itemId=" + itemId + ", itemName=" + itemName
                + ", itemDescription=" + itemDescription
                + ", itemPrice=" + itemPrice
                + ", quantity=" + quantity +"]";
    }
}
