package com.hackathon.hcl.controller;

import com.hackathon.hcl.model.MenuItem;
import com.hackathon.hcl.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemRepository menuItemRepository;

    @GetMapping("/{id}")
    public MenuItem one(@PathVariable Integer id) {
        return menuItemRepository.findById(id).orElseThrow();
    }
}
