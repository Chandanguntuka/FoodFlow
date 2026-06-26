package com.hackathon.hcl.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private Integer restaurantId;
    private String deliveryAddress;
    private String address;
    private String paymentMethod;
    private List<OrderItemRequestDTO> items;
}
