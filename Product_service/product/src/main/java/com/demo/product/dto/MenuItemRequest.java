package com.demo.product.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemRequest {
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private String imageUrl;
    private Boolean isVegetarian;
    private Long categoryId;
}