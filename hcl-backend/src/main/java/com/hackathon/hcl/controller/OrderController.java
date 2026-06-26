package com.hackathon.hcl.controller;

import com.hackathon.hcl.DTO.OrderRequestDTO;
import com.hackathon.hcl.DTO.OrderItemResponseDTO;
import com.hackathon.hcl.DTO.OrderResponseDTO;
import com.hackathon.hcl.DTO.OrderStatusUpdateRequestDTO;
import com.hackathon.hcl.model.*;
import com.hackathon.hcl.repository.MenuItemRepository;
import com.hackathon.hcl.repository.OrderRepository;
import com.hackathon.hcl.repository.RestaurantRepository;
import com.hackathon.hcl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @PostMapping
    public OrderResponseDTO place(@RequestBody OrderRequestDTO request, Authentication authentication) {
        User user = userRepository.findById(Integer.valueOf(authentication.getName())).orElseThrow();
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow();
        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPaymentMethod(request.getPaymentMethod() == null ? "COD" : request.getPaymentMethod());
        order.setDeliveryAddress(request.getDeliveryAddress() == null ? request.getAddress() : request.getDeliveryAddress());

        BigDecimal total = BigDecimal.ZERO;
        for (var line : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(line.getMenuItemId()).orElseThrow();
            OrderItem orderItem = new OrderItem();
            int quantity = line.getQuantity() == null ? 1 : line.getQuantity();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(menuItem.getPrice());
            order.getItems().add(orderItem);
            total = total.add(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        order.setTotalAmount(total.add(BigDecimal.valueOf(40)));
        return toResponse(orderRepository.save(order));
    }

    @GetMapping("/my")
    public List<OrderResponseDTO> myOrders(Authentication authentication) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(Integer.valueOf(authentication.getName()))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO one(@PathVariable Integer id, Authentication authentication) {
        return toResponse(findAccessibleOrder(id, authentication));
    }

    private Order findAccessibleOrder(Integer id, Authentication authentication) {
        Order order = orderRepository.findWithDetailsById(id).orElseThrow();
        Integer userId = Integer.valueOf(authentication.getName());
        if (!order.getUser().getId().equals(userId) && authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ADMIN"))) {
            throw new IllegalStateException("Cannot access this order");
        }
        return order;
    }

    @PutMapping("/{id}/status")
    public OrderResponseDTO updateStatus(@PathVariable Integer id, @RequestBody OrderStatusUpdateRequestDTO request, Authentication authentication) {
        Order order = findAccessibleOrder(id, authentication);
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        OrderStatus nextStatus = OrderStatus.valueOf(request.getStatus().toUpperCase());
        if (!isAdmin && nextStatus != OrderStatus.CANCELLED) {
            throw new IllegalStateException("Only admins can update order status");
        }
        if (!isAdmin && order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Only pending or confirmed orders can be cancelled");
        }
        order.setStatus(nextStatus);
        Order savedOrder = orderRepository.save(order);
        return toResponse(orderRepository.findWithDetailsById(savedOrder.getId()).orElseThrow());
    }

    private OrderResponseDTO toResponse(Order order) {
        List<OrderItemResponseDTO> items = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            MenuItem menuItem = item.getMenuItem();
            items.add(new OrderItemResponseDTO(
                    item.getId(),
                    order.getId(),
                    menuItem.getId(),
                    menuItem.getName(),
                    item.getQuantity(),
                    item.getPrice()));
        }
        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getName(),
                order.getRestaurant().getId(),
                order.getRestaurant().getName(),
                order.getStatus().name(),
                order.getPaymentStatus().name(),
                order.getPaymentMethod(),
                order.getTotalAmount(),
                order.getDeliveryAddress(),
                order.getDeliveryAddress(),
                order.getCreatedAt(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                items,
                items);
    }
}
