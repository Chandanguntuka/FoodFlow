package com.hackathon.hcl.controller;

import com.hackathon.hcl.DTO.UserResponseDTO;
import com.hackathon.hcl.model.User;
import com.hackathon.hcl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/profile")
    public UserResponseDTO profile(Authentication authentication) {
        User user = userRepository.findById(Integer.valueOf(authentication.getName())).orElseThrow();
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole().name(), user.getCreatedAt());
    }
}
