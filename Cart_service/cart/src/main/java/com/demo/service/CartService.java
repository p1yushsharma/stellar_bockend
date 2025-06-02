package com.demo.cart.service;
import com.demo.cart.dto.CartItemRequest;
import com.demo.cart.dto.CartResponse;
import com.demo.cart.entity.Cart;
import com.demo.cart.entity.CartItem;
import com.demo.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public CartResponse addToCart(CartItemRequest itemRequest) {
        Long userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId).orElse(
            Cart.builder()
                .userId(userId)
                .build()
        );

        CartItem item = CartItem.builder()
                .productId(itemRequest.getProductId())
                .quantity(itemRequest.getQuantity())
                .cart(cart)
                .build();

        cart.getItems().add(item);
        Cart saved = cartRepository.save(cart);

        return toResponse(saved);
    }

    public CartResponse getCart() {
        Long userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return toResponse(cart);
    }

    public CartResponse updateCartItem(CartItemRequest itemRequest) {
        Long userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(itemRequest.getProductId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        existingItem.setQuantity(itemRequest.getQuantity());

        Cart saved = cartRepository.save(cart);
        return toResponse(saved);
    }

    public CartResponse removeCartItem(Long productId) {
        Long userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(i -> i.getProductId().equals(productId));

        Cart saved = cartRepository.save(cart);
        return toResponse(saved);
    }

    public void clearCart() {
        Long userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private CartResponse toResponse(Cart cart) {
        List<CartItemRequest> items = cart.getItems().stream()
                .map(i -> new CartItemRequest(i.getProductId(), i.getQuantity()))
                .collect(Collectors.toList());

        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(cart.getUserId())
                .items(items)
                .build();
    }

    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            return Long.parseLong((String) principal);
        }
        throw new RuntimeException("Invalid user context");
    }
} 
