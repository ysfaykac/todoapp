package com.example.todoapp.services;

import com.example.todoapp.dto.role.CreateRoleDto;
import com.example.todoapp.dto.role.RoleDto;
import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.dto.user.UserDto;
import com.example.todoapp.entity.Role;
import com.example.todoapp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto save(CreateUserDto user);
    RoleDto save(CreateRoleDto role);
    void addRoleToUser(String username, String roleName);
    UserDto getUser(String username);
    List<UserDto> getUsers();

}
