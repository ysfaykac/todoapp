package com.example.todoapp.exceptions;

public class TodoIsNotValidException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public TodoIsNotValidException() {
        super("Todo is not valid for you.");
    }
}
