package com.example.todoapp.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
