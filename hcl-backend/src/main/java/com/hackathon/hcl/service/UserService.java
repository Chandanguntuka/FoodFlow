package com.hackathon.hcl.service;

import com.hackathon.hcl.DTO.UserResponseDTO;
import com.hackathon.hcl.model.User;
import com.hackathon.hcl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO getUserById(Integer id) {
        return toResponse(userRepository.findById(id).orElseThrow());
    }

    private UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole().name(), user.getCreatedAt());
    }
}
