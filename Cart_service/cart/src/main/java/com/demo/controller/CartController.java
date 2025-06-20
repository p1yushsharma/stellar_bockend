package com.demo.cart.controller;
import com.demo.cart.dto.CartItemRequest;
import com.demo.cart.dto.CartResponse;
import com.demo.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
   

    @PostMapping("/add")
    public CartResponse addItemToCart(@RequestBody CartItemRequest request) {
        return cartService.addToCart(request);
    }

    @GetMapping("/get")
    public CartResponse getCart() {
        return cartService.getCart();
    }

    @PutMapping("/update")
    public CartResponse updateItemInCart(@RequestBody CartItemRequest request) {
        return cartService.updateCartItem(request);
    }

    @DeleteMapping("/remove/{productId}")
    public CartResponse removeItemFromCart(@PathVariable Long productId) {
        return cartService.removeCartItem(productId);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }
}
