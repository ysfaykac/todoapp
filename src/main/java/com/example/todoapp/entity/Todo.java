package com.example.todoapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "todos")
@Table()
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private boolean finished;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
