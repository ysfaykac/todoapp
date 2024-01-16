package com.example.todoapp.services;

import com.example.todoapp.dto.auth.LoginDto;
import com.example.todoapp.dto.auth.TokenDto;
import com.example.todoapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public TokenDto login(LoginDto loginDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());
        var authUser = authenticationManager.authenticate(usernamePassword);
        var accessToken = jwtService.generateAccessToken((User) authUser.getPrincipal());
        return new TokenDto(accessToken);
    }
}
