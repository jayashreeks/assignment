package com.ecommerce.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date orderDate;
    private Double totalAmount;
    private Long orderCount;
    private Long discountCode;
    private Double totalDiscountAmount;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OrderItem> orderItemList=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Order [id=" + id + ", orderDate=" + orderDate
                + ", totalAmount=" + totalAmount
                + ", orderCount=" + orderCount
                + ", discountCode=" + discountCode
                + ", totalDiscountAmount=" + totalDiscountAmount
                + ", orderStatus=" + orderStatus
                + ", user=" + user+"]";
    }

}

