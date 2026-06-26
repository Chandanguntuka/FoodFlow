package com.hackathon.hcl.controller;

import com.hackathon.hcl.model.MenuItem;
import com.hackathon.hcl.model.Restaurant;
import com.hackathon.hcl.repository.MenuItemRepository;
import com.hackathon.hcl.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @GetMapping
    public Page<Restaurant> all(@RequestParam(defaultValue = "") String search,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "60") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        if (search == null || search.isBlank()) {
            return restaurantRepository.findAll(pageable);
        }
        return restaurantRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(search, search, pageable);
    }

    @GetMapping("/{id}")
    public Restaurant one(@PathVariable Integer id) {
        return restaurantRepository.findById(id).orElseThrow();
    }

    @GetMapping("/{id}/menu")
    public List<MenuItem> menu(@PathVariable Integer id) {
        return menuItemRepository.findByRestaurantId(id);
    }
}
