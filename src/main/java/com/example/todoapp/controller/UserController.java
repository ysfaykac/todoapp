package com.example.todoapp.controller;

import com.example.todoapp.dto.auth.LoginDto;
import com.example.todoapp.dto.auth.RegisterUserDto;
import com.example.todoapp.dto.auth.TokenDto;
import com.example.todoapp.services.AuthService;
import com.example.todoapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> signIn(@RequestBody @Valid LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody @Valid RegisterUserDto data) {
        userService.register(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
