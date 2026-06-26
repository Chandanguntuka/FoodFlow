package com.hackathon.hcl.controller;

import com.hackathon.hcl.DTO.MenuItemRequestDTO;
import com.hackathon.hcl.DTO.OrderItemResponseDTO;
import com.hackathon.hcl.DTO.OrderResponseDTO;
import com.hackathon.hcl.DTO.OrderStatusUpdateRequestDTO;
import com.hackathon.hcl.DTO.RestaurantRequestDTO;
import com.hackathon.hcl.model.MenuItem;
import com.hackathon.hcl.model.Order;
import com.hackathon.hcl.model.OrderStatus;
import com.hackathon.hcl.model.Restaurant;
import com.hackathon.hcl.repository.MenuItemRepository;
import com.hackathon.hcl.repository.OrderRepository;
import com.hackathon.hcl.repository.RestaurantRepository;
import com.hackathon.hcl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        BigDecimal revenue = orderRepository.findAll().stream()
                .filter(order -> order.getPaymentStatus().name().equals("PAID"))
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return Map.of(
                "totalOrders", orderRepository.count(),
                "totalRestaurants", restaurantRepository.count(),
                "totalUsers", userRepository.count(),
                "revenue", revenue
        );
    }

    @PostMapping("/restaurants")
    public Restaurant createRestaurant(@RequestBody RestaurantRequestDTO request) {
        return restaurantRepository.save(apply(new Restaurant(), request));
    }

    @PutMapping("/restaurants/{id}")
    public Restaurant updateRestaurant(@PathVariable Integer id, @RequestBody RestaurantRequestDTO request) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        return restaurantRepository.save(apply(restaurant, request));
    }

    @DeleteMapping("/restaurants/{id}")
    public void deleteRestaurant(@PathVariable Integer id) {
        restaurantRepository.deleteById(id);
    }

    @PostMapping("/restaurants/{id}/menu")
    public MenuItem createMenuItem(@PathVariable Integer id, @RequestBody MenuItemRequestDTO request) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        MenuItem item = apply(new MenuItem(), request);
        item.setRestaurant(restaurant);
        return menuItemRepository.save(item);
    }

    @PutMapping("/menu/{itemId}")
    public MenuItem updateMenuItem(@PathVariable Integer itemId, @RequestBody MenuItemRequestDTO request) {
        MenuItem item = menuItemRepository.findById(itemId).orElseThrow();
        return menuItemRepository.save(apply(item, request));
    }

    @DeleteMapping("/menu/{itemId}")
    public void deleteMenuItem(@PathVariable Integer itemId) {
        menuItemRepository.deleteById(itemId);
    }

    @GetMapping("/orders")
    public List<OrderResponseDTO> allOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }

    @PutMapping("/orders/{id}/status")
    public OrderResponseDTO updateStatus(@PathVariable Integer id, @RequestBody OrderStatusUpdateRequestDTO request) {
        Order order = orderRepository.findWithDetailsById(id).orElseThrow();
        order.setStatus(OrderStatus.valueOf(request.getStatus().toUpperCase()));
        Order savedOrder = orderRepository.save(order);
        return toOrderResponse(orderRepository.findWithDetailsById(savedOrder.getId()).orElseThrow());
    }

    private Restaurant apply(Restaurant restaurant, RestaurantRequestDTO request) {
        restaurant.setName(request.getName());
        restaurant.setCuisine(request.getCuisine());
        restaurant.setLocation(request.getLocation());
        restaurant.setImageUrl(request.getImageUrl());
        restaurant.setRating(request.getRating());
        restaurant.setDeliveryTime(request.getDeliveryTime());
        restaurant.setMinOrder(request.getMinOrder());
        restaurant.setIsOpen(request.getIsOpen() == null || request.getIsOpen());
        return restaurant;
    }

    private MenuItem apply(MenuItem item, MenuItemRequestDTO request) {
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setImageUrl(request.getImageUrl());
        item.setCategory(request.getCategory());
        item.setIsVeg(request.getIsVeg());
        item.setRating(request.getRating());
        item.setPreparationTime(request.getPreparationTime());
        item.setIsPopular(request.getIsPopular() != null && request.getIsPopular());
        item.setIsAvailable(request.getIsAvailable() == null || request.getIsAvailable());
        return item;
    }

    private OrderResponseDTO toOrderResponse(Order order) {
        List<OrderItemResponseDTO> items = new ArrayList<>();
        for (var item : order.getItems()) {
            var menuItem = item.getMenuItem();
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
