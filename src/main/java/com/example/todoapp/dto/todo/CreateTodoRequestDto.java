package com.example.todoapp.dto.todo;

import jakarta.validation.constraints.NotNull;

public record CreateTodoRequestDto(@NotNull String description) {
}
