package com.example.todoapp.services;

import com.example.todoapp.dto.role.CreateRoleDto;
import com.example.todoapp.dto.role.RoleDto;
import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.dto.user.UserDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.todoapp.entity.User;
import com.example.todoapp.mapper.CustomMapper;
import com.example.todoapp.repositories.RoleRepository;
import com.example.todoapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomMapper customMapper;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    @Transactional
    public UserDto save(CreateUserDto createUserDto) {
        User user = customMapper.createUserDtoToUser(createUserDto);
        user.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));
        return customMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public RoleDto save(CreateRoleDto createRoleDto) {
        var role = customMapper.createRoleDtoToRole(createRoleDto);
        return customMapper.roleToRoleDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void addRoleToUser(String username, String roleName) {
        var user = userRepository.findByUsername(username);
        var role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public UserDto getUser(String username) {
        return customMapper.userToUserDto(userRepository.findByUsername(username));

    }

    @Override
    public List<UserDto> getUsers() {
        return customMapper.userListToUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("User not found");
       return user;

    }
}
