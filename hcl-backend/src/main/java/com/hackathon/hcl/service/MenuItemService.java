package com.hackathon.hcl.service;

import com.hackathon.hcl.model.MenuItem;
import com.hackathon.hcl.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public List<MenuItem> getMenuItemsByRestaurant(Integer restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public MenuItem getMenuItemById(Integer id) {
        return menuItemRepository.findById(id).orElseThrow();
    }
}
