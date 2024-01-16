package com.example.todoapp.repository;

import com.example.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    @Query("select t from todos t where t.user.id = ?1")
    List<Todo> findByUserId(Long id);
}
