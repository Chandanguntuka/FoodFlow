package com.hackathon.hcl.DTO;

import lombok.Data;

@Data
public class RestaurantRequestDTO {
    private String name;
    private String cuisine;
    private String location;
    private String imageUrl;
    private Double rating;
    private String deliveryTime;
    private Integer minOrder;
    private Boolean isOpen;
}
