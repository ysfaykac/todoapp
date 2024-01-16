package com.example.todoapp.controller;

import com.example.todoapp.dto.todo.CreateTodoRequestDto;
import com.example.todoapp.dto.todo.TodoDto;
import com.example.todoapp.dto.todo.UpdateTodoRequestDto;
import com.example.todoapp.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping()
    public ResponseEntity<Set<TodoDto>> getUserTodos() {
        return ResponseEntity.ok(todoService.getTodosByUserId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTaskById(
            @PathVariable Long id) {
        return ResponseEntity.ok(todoService.findTodoById(id));
    }

    @PostMapping()
    public ResponseEntity<TodoDto> createTodo(
            @Valid @RequestBody CreateTodoRequestDto requestDto) {
        return ResponseEntity.ok(todoService.createTodo(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTodoRequestDto requestDto)
    {
        return ResponseEntity.ok(todoService.updateTodo(id,requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
