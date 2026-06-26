package com.hackathon.hcl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String cuisine;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String location;

    @Column(nullable = false, length = 600)
    private String imageUrl;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false, length = 40)
    private String deliveryTime;

    @Column(nullable = false)
    private Integer minOrder;

    @Column(nullable = false)
    private Boolean isOpen = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

    @PrePersist
    void onCreate() {
        if (isOpen == null) {
            isOpen = true;
        }
        createdAt = LocalDateTime.now();
    }
}
