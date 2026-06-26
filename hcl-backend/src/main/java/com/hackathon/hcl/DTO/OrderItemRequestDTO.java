package com.hackathon.hcl.DTO;

import lombok.Data;

@Data
public class OrderItemRequestDTO {
    private Integer menuItemId;
    private Integer quantity;
}
