package com.example.todoapp.services;

import com.example.todoapp.dto.todo.CreateTodoRequestDto;
import com.example.todoapp.dto.todo.TodoDto;
import com.example.todoapp.dto.todo.UpdateTodoRequestDto;

import java.util.Set;

public interface TodoService {
    TodoDto findTodoById(Long id);
    Set<TodoDto> getTodosByUserId();
    TodoDto createTodo(CreateTodoRequestDto requestDto);
    void deleteTodo(Long id);
    TodoDto updateTodo(Long id,UpdateTodoRequestDto requestDto);
}
