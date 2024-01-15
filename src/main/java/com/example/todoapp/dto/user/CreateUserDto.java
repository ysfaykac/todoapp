package com.example.todoapp.dto.user;

import com.example.todoapp.dto.role.CreateRoleDto;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto
{
    private String username;
    private String password;
    private String name;
    private Set<CreateRoleDto> roleDtos;
}
