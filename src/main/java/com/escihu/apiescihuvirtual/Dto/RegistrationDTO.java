package com.escihu.apiescihuvirtual.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationDTO(
        @NotBlank(message = "Username is necessary")
        String username,
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is necessary")
        String email,
        @Size(min = 2, max = 20, message = "Password must be between {min} and {max} characters")
        String password,
        Long userAsigned) {
}
