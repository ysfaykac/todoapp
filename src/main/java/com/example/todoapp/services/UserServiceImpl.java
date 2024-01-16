package com.example.todoapp.services;

import com.example.todoapp.dto.auth.RegisterUserDto;
import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.dto.user.UserDto;
import com.example.todoapp.entity.User;
import com.example.todoapp.entity.UserRole;
import com.example.todoapp.exceptions.UserAlreadyExistsException;
import com.example.todoapp.mapper.CustomMapper;
import com.example.todoapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final CustomMapper customMapper;
    @Override
    public UserDto save(CreateUserDto createUserDto) {
        User user = customMapper.createUserDtoToUser(createUserDto);
        String encryptedPassword = new BCryptPasswordEncoder().encode(createUserDto.getPassword());
        user.setPassword(encryptedPassword);
        return customMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public void addRoleToUser(String username, UserRole roleName) {
        var user = userRepository.findUserByUsername(username);
        user.setRole(roleName);
        userRepository.save(user);
    }

    @Override
    public UserDto getUser(String username) {
        return customMapper.userToUserDto(userRepository.findUserByUsername(username));
    }
    @Override
    public List<UserDto> getUsers() {
        return customMapper.userListToUserDtoList(userRepository.findAll());
    }
    @Override
    public void register(RegisterUserDto data) {
        var user= userRepository.findUserByUsername(data.username());
        if(user!=null){
            throw new UserAlreadyExistsException();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(),data.username(), encryptedPassword, data.role());
        userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("User not found");
        return user;
    }

    @Override
    public UserDto getAuthentication() {
        var auth= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user=userRepository.findUserByUsername(auth.getUsername());
        return customMapper.userToUserDto(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
