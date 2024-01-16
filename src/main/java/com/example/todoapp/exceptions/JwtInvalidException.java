package com.example.todoapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtInvalidException extends AuthenticationException {
    public JwtInvalidException(String msg) {
        super(msg);
    }
}
