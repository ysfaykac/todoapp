package com.example.todoapp.dto.auth;

import com.example.todoapp.entity.UserRole;

public record RegisterUserDto(
        String name,
        String username,
        String password,
        UserRole role   ) {
}
