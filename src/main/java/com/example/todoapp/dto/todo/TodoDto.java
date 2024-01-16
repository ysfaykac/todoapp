package com.example.todoapp.dto.todo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TodoDto {
    private Long id;
    private String description;
    private boolean finished;
    private String userName;
}
