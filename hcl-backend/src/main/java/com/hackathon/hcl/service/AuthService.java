package com.hackathon.hcl.service;

import com.hackathon.hcl.DTO.AuthResponseDTO;
import com.hackathon.hcl.DTO.LoginRequestDTO;
import com.hackathon.hcl.DTO.UserRequestDTO;
import com.hackathon.hcl.DTO.UserResponseDTO;
import com.hackathon.hcl.exception.BadRequestException;
import com.hackathon.hcl.model.Role;
import com.hackathon.hcl.model.User;
import com.hackathon.hcl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Transactional
    public AuthResponseDTO register(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }
        if (request.getPhone() != null && !request.getPhone().isBlank() && userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Phone is already registered");
        }

        User user = new User();
        user.setName(resolveName(request));
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(normalizeRole(request.getRole()));

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return new AuthResponseDTO(token, "Bearer", toUserResponse(savedUser), savedUser.getRole().name());
    }

    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token, "Bearer", toUserResponse(user), user.getRole().name());
    }

    public void logout(String authorizationHeader) {
        jwtService.invalidateToken(authorizationHeader);
    }

    private UserResponseDTO toUserResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole().name(),
                user.getCreatedAt());
    }

    private Role normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return Role.USER;
        }
        String normalized = role.trim().toUpperCase();
        if ("CUSTOMER".equals(normalized) || "RESTAURANT".equals(normalized)) {
            return Role.USER;
        }
        return Role.valueOf(normalized);
    }

    private String resolveName(UserRequestDTO request) {
        if (request.getName() != null && !request.getName().isBlank()) {
            return request.getName().trim();
        }
        return (String.join(" ",
                request.getFirstName() == null ? "" : request.getFirstName(),
                request.getLastName() == null ? "" : request.getLastName())).trim();
    }
}
