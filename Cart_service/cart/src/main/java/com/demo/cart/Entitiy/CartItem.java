package com.demo.cart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items", schema = "foodies_carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
