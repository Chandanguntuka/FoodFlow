package com.hackathon.hcl.service;

import com.hackathon.hcl.model.Restaurant;
import com.hackathon.hcl.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Integer id) {
        return restaurantRepository.findById(id).orElseThrow();
    }
}
