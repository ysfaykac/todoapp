package com.example.todoapp.services;

import com.example.todoapp.dto.auth.RegisterUserDto;
import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.dto.user.UserDto;
import com.example.todoapp.entity.User;
import com.example.todoapp.entity.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto save(CreateUserDto user);
    void addRoleToUser(String username, UserRole roleName);
    UserDto getUser(String username);
    List<UserDto> getUsers();
    void register(RegisterUserDto data);
    UserDto getAuthentication();
    User findUserById(Long id);

}
