package com.hackathon.hcl.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String firstName;
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role;
    private String phone;
    private String address;
}
