package com.ecommerce.assignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;

    private String lastName;

    @Size(min = 10, max = 10, message = "Mobile Number has be exactly 10 digits")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number must have only numbers")
    private String mobileNumber;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL )
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Cart cart;

    public Long getId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", mobileNumber=" + mobileNumber
                + ", email=" + email+"]";
    }
}
