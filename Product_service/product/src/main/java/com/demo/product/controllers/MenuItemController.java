package com.demo.product.controller;
import com.demo.product.dto.CategoryResponse;
import com.demo.product.dto.MenuItemRequest;
import com.demo.product.dto.MenuItemResponse;
import com.demo.product.entity.MenuItem;
import com.demo.product.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

 
    private MenuItemResponse toResponse(MenuItem menuItem) {
        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .available(menuItem.getAvailable())
                .imageUrl(menuItem.getImageUrl())
                .isVegetarian(menuItem.getIsVegetarian())
                .category(
                    CategoryResponse.builder()
                        .id(menuItem.getCategory().getId())
                        .name(menuItem.getCategory().getName())
                        .build()
                )
                .build();
    }

    @PostMapping("/create")
    public ResponseEntity<MenuItemResponse> createMenuItem(@RequestBody MenuItemRequest request) {
        MenuItem saved = menuItemService.saveMenuItem(request);
        return ResponseEntity.ok(toResponse(saved));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequest request) {
        MenuItem updated = menuItemService.updateMenuItem(id, request);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItem(@PathVariable Long id) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(toResponse(menuItem));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems() {
        List<MenuItem> items = menuItemService.getAllMenuItems();
        List<MenuItemResponse> responses = items.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
