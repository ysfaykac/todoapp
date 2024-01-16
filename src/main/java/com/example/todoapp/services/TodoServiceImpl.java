package com.example.todoapp.services;

import com.example.todoapp.dto.todo.CreateTodoRequestDto;
import com.example.todoapp.dto.todo.TodoDto;
import com.example.todoapp.dto.todo.UpdateTodoRequestDto;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.exceptions.TodoIsNotValidException;
import com.example.todoapp.mapper.CustomMapper;
import com.example.todoapp.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final UserService userService;
    private final TodoRepository todoRepository;
    private final CustomMapper customMapper;
    @Override
    public TodoDto findTodoById(Long id) {
        var todo=todoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var userDto= userService.getAuthentication();
        if(!todo.getUser().getId().equals(userDto.getId())){
            throw new TodoIsNotValidException();
        }
        return customMapper.todoToTodoDto(todo);
    }

    @Override
    public Set<TodoDto> getTodosByUserId() {
        var userDto= userService.getAuthentication();
        return customMapper.todosToTodoDtos(todoRepository.findByUserId(userDto.getId()));
    }

    @Override
    public TodoDto createTodo(CreateTodoRequestDto requestDto) {
        var userDto= userService.getAuthentication();
        var user= userService.findUserById(userDto.getId());
        var todo = Todo.builder()
                .description(requestDto.description())
                .finished(false)
                .user(user)
                .build();
        return customMapper.todoToTodoDto(todoRepository.save(todo));
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto updateTodo(Long id,UpdateTodoRequestDto requestDto) {
        var todo = todoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        todo.setDescription(requestDto.description());
        todo.setFinished(requestDto.isFinished());
        return customMapper.todoToTodoDto(todoRepository.save(todo));
    }
}
