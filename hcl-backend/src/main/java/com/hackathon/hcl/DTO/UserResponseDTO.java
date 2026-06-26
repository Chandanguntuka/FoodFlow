package com.hackathon.hcl.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String role;
    private LocalDateTime createdAt;
}
