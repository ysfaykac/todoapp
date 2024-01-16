package com.example.todoapp.entity;

public enum UserRole {
    ADMIN("admin"),
    USER("user");
    private String role;
    UserRole(String role) {
        this.role = role;
    }
    public String value() {
        return role;
    }
}
