package com.demo.product.service;
import com.demo.product.dto.CategoryResponse;
import com.demo.product.dto.CategoryRequest;
import com.demo.product.entity.Category;
import com.demo.product.dto.MenuItemRequest;
import com.demo.product.entity.MenuItem;
import com.demo.product.repository.MenuItemRepository;
import com.demo.product.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository; 
    public MenuItem saveMenuItem(MenuItemRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        MenuItem menuItem = MenuItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .available(request.getAvailable())
                .imageUrl(request.getImageUrl())
                .isVegetarian(request.getIsVegetarian())
                .category(category)
                .build();
        return menuItemRepository.save(menuItem);
    }

    public MenuItem updateMenuItem(Long id, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setAvailable(request.getAvailable());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setIsVegetarian(request.getIsVegetarian());
        menuItem.setCategory(category);

        return menuItemRepository.save(menuItem);
    }



    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));
        menuItemRepository.delete(menuItem);
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }
}

