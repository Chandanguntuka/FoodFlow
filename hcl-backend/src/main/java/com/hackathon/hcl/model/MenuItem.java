package com.hackathon.hcl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 600)
    private String imageUrl;

    @Column(nullable = false, length = 60)
    private String category;

    @Column(nullable = false)
    private Boolean isVeg;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private Integer preparationTime;

    @Column(nullable = false)
    private Boolean isPopular;

    @Column(nullable = false)
    private Boolean isAvailable = true;
}
