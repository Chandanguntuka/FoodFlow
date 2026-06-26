package com.hackathon.hcl.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItemRequestDTO {
    private Integer restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String category;
    private Boolean isVeg;
    private Double rating;
    private Integer preparationTime;
    private Boolean isPopular;
    private Boolean isAvailable;
}
