package com.example.todoapp.controller;

import com.example.todoapp.dto.role.CreateRoleDto;
import com.example.todoapp.dto.role.RoleDto;
import com.example.todoapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(CreateRoleDto roleDto) {
        return ResponseEntity.ok(userService.save(roleDto));
    }
}
