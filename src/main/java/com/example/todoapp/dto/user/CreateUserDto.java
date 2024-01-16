package com.example.todoapp.dto.user;

import com.example.todoapp.entity.UserRole;
import lombok.*;

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
    private UserRole userRole;
}
