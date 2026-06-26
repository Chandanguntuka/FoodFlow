package com.hackathon.hcl.controller;

import com.hackathon.hcl.DTO.DummyPaymentRequestDTO;
import com.hackathon.hcl.model.Order;
import com.hackathon.hcl.model.OrderStatus;
import com.hackathon.hcl.model.PaymentStatus;
import com.hackathon.hcl.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final OrderRepository orderRepository;

    @PostMapping("/dummy")
    public Map<String, Object> dummy(@RequestBody DummyPaymentRequestDTO request) throws InterruptedException {
        Thread.sleep(2000);
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow();
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setStatus(OrderStatus.CONFIRMED);
        if (request.getMethod() != null && !request.getMethod().isBlank()) {
            order.setPaymentMethod(request.getMethod());
        }
        orderRepository.save(order);
        return Map.of(
                "success", true,
                "transactionId", "TXN_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                "orderId", order.getId()
        );
    }
}
