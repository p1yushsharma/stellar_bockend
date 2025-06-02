package com.demo.product.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories",schema="foodies_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

}
