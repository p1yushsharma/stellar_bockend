package com.demo.product.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private String imageUrl;
    private Boolean isVegetarian;
    private CategoryResponse category; 
}
