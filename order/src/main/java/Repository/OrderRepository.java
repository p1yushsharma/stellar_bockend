package com.demo.order.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.demo.order.entity.Order;
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId); 
}
