package com.demo.product.repository;
import com.demo.product.entity.MenuItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
  
}