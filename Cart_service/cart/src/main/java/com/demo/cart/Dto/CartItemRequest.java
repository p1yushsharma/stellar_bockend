package com.demo.cart.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}
