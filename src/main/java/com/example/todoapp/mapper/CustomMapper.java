package com.example.todoapp.mapper;

import com.example.todoapp.dto.role.CreateRoleDto;
import com.example.todoapp.dto.role.RoleDto;
import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.dto.user.UserDto;
import com.example.todoapp.entity.Role;
import com.example.todoapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomMapper {

    public User createUserDtoToUser(CreateUserDto createUserDto) {

        return User.builder()
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .name(createUserDto.getName())
                .roles(createRolesDtoToRoles(createUserDto.getRoleDtos()))
                .build();
    }

    public UserDto userToUserDto(User user) {

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }

 public Role createRoleDtoToRole(CreateRoleDto createRoleDto) {
        return Role.builder()
                .name(createRoleDto.getName())
                .build();
    }
    public Set<Role> createRolesDtoToRoles(Set<CreateRoleDto> createRoleDto) {
        return createRoleDto.stream().map(this::createRoleDtoToRole).collect(Collectors.toSet());
    }

    public RoleDto roleToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public List<UserDto> userListToUserDtoList(List<User> all) {
        return  all.stream().map(this::userToUserDto).toList();
    }
}
