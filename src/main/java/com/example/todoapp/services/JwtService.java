package com.example.todoapp.services;

import com.example.todoapp.entity.User;

public interface JwtService {
    String generateAccessToken(User user);
    String validateToken(String token);

}
