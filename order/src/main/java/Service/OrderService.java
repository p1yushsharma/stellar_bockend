package com.demo.order.service;
import com.demo.order.dto.OrderItemRequest;
import com.demo.order.dto.OrderRequest;
import com.demo.order.dto.OrderResponse;
import com.demo.order.entity.Order;
import com.demo.order.entity.OrderItem;
import com.demo.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        Long userId = getCurrentUserId();

        
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());

        
        List<OrderItem> items = request.getItems().stream()
                .map(itemRequest -> OrderItem.builder()
                        .productId(itemRequest.getProductId())
                        .quantity(itemRequest.getQuantity())
                        .priceAtPurchase(itemRequest.getPriceAtPurchase())
                        .order(order) 
                        .build())
                .collect(Collectors.toList());

       
        order.setItems(items);
        double totalAmount = items.stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);

    
        Order savedOrder = orderRepository.save(order);

      
        return OrderResponse.from(savedOrder); 
    }

    public List<OrderResponse> getUserOrders() {
        Long userId = getCurrentUserId();
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }


private Long getCurrentUserId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof String) {
        try {
            return Long.parseLong((String) principal);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid user ID format: " + principal);
        }
    }
    throw new RuntimeException("Invalid user context: " + principal.getClass().getName());
}

}