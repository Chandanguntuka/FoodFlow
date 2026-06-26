package com.hackathon.hcl.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    private Integer id;
    private Integer userId;
    private String userName;
    private Integer restaurantId;
    private String restaurantName;
    private String status;
    private String paymentStatus;
    private String paymentMethod;
    private BigDecimal totalAmount;
    private String address;
    private String deliveryAddress;
    private LocalDateTime createdAt;
    private LocalDateTime placedAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponseDTO> items;
    private List<OrderItemResponseDTO> orderItems;
}
