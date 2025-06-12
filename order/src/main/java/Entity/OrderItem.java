package com.demo.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items", schema = "foodies_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantity;

    private Double priceAtPurchase;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}

