package com.example.java_version.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank(message = "O campo [username] não pode ser vazio!")
        @Size(min = 2, message = "O campo [username] deve ter mais do que 2 caracteres!")
        String username,

        @NotBlank(message = "O campo [email] é obrigatório e não pode ser vazio!")
        @Email
        String email,

        @NotBlank(message = "O campo [password] não pode ser vazio!")
        @Size(min = 8, message = "O campo [password] deve ter mais do que 8 caracteres!")
        String password
) {}
