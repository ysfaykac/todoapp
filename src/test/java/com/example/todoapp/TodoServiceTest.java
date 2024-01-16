package com.example.todoapp;


import com.example.todoapp.dto.todo.TodoDto;
import com.example.todoapp.dto.todo.UpdateTodoRequestDto;
import com.example.todoapp.dto.user.UserDto;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.exceptions.TodoIsNotValidException;
import com.example.todoapp.mapper.CustomMapper;
import com.example.todoapp.repository.TodoRepository;
import com.example.todoapp.services.TodoServiceImpl;
import com.example.todoapp.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private CustomMapper customMapper;

    @InjectMocks
    private TodoServiceImpl todoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindTodoById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long id = 1L;

        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            todoService.findTodoById(id);
        });
        verify(todoRepository, times(1)).findById(id);
        verify(userService, never()).getAuthentication();
        verify(customMapper, never()).todoToTodoDto(any());
    }

    @Test
    public void testDeleteTodo_WithValidId_ShouldCallDeleteById() {
        // Arrange
        Long id = 1L;

        // Act
        todoService.deleteTodo(id);

        // Assert
        verify(todoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTodo_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long id = 1L;
        UpdateTodoRequestDto requestDto = new UpdateTodoRequestDto("Updated Description",true);

        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            todoService.updateTodo(id, requestDto);
        });
        verify(todoRepository, times(1)).findById(id);
        verify(todoRepository, never()).save(any());
        verify(customMapper, never()).todoToTodoDto(any());
    }
}
