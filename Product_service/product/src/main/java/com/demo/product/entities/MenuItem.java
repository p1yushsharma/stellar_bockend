package com.demo.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_items",schema="foodies_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    private Boolean available;

   

    private String imageUrl;

    private Boolean isVegetarian;

    @ManyToOne
    @JoinColumn(name = "category_id" ,referencedColumnName = "id", nullable = false)
    private Category category;
}
