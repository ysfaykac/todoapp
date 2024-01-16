package com.example.todoapp.mapper;

import com.example.todoapp.dto.todo.TodoDto;
import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.dto.user.UserDto;
import com.example.todoapp.entity.Todo;
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
                .role(createUserDto.getUserRole())
                .build();
    }
    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
    public List<UserDto> userListToUserDtoList(List<User> all) {
        return all.stream().map(this::userToUserDto).toList();
    }

    public TodoDto todoToTodoDto(Todo todo) {
        return TodoDto.builder()
                .description(todo.getDescription())
                .finished(todo.isFinished())
                .id(todo.getId())
                .userName(todo.getUser().getName())
                .build();
    }

    public Set<TodoDto> todosToTodoDtos(List<Todo> todos) {
        return todos.stream().map(this::todoToTodoDto).collect(Collectors.toSet());
    }
}
