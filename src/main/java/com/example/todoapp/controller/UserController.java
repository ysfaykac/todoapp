package com.example.todoapp.controller;

import com.example.todoapp.dto.role.CreateUserRoleRequest;
import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.dto.user.UserDto;
import com.example.todoapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(CreateUserDto userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }

    @PatchMapping
    public ResponseEntity<?> addRoleToUser(@RequestBody CreateUserRoleRequest request) {
        userService.addRoleToUser(request.getUsername(), request.getRoleName());
        return ResponseEntity.ok().build();
    }

}
