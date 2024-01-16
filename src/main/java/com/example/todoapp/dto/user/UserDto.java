package com.example.todoapp.dto.user;
import com.example.todoapp.entity.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private UserRole role;
}
