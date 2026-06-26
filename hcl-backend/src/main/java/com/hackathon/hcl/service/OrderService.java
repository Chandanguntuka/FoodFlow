package com.hackathon.hcl.service;

import com.hackathon.hcl.model.Order;
import com.hackathon.hcl.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow();
    }
}
