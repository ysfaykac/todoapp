package com.example.todoapp.dto.role;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRoleRequest {
    private String username;
    private String roleName;
}
