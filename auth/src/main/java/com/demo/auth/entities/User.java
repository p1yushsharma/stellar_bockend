package com.demo.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "foodies_users", name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    private String role;

    @PrePersist
    public void setDefaultRole() {
        if (this.role == null || this.role.isBlank()) {
            this.role = "USER";
        }
    }
}
