package com.ecommerce.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "discount_code")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DiscountCode {

    @Id
    @GeneratedValue
    private long id;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
