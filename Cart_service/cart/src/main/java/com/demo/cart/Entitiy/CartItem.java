package com.demo.cart.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(
    name = "cart_items",
    schema = "foodies_carts",
    uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}


