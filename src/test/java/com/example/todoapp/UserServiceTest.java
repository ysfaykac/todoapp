package com.example.todoapp;

import com.example.todoapp.dto.auth.RegisterUserDto;
import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.dto.user.UserDto;
import com.example.todoapp.entity.User;
import com.example.todoapp.entity.UserRole;
import com.example.todoapp.exceptions.UserAlreadyExistsException;
import com.example.todoapp.mapper.CustomMapper;
import com.example.todoapp.repository.UserRepository;
import com.example.todoapp.services.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomMapper customMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void save_shouldSaveUserWithEncryptedPassword() {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setPassword("password");

        User user = new User();
        UserDto userDto=new UserDto();
        when(customMapper.createUserDtoToUser(createUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(customMapper.userToUserDto(user)).thenReturn(userDto);


        // Act
        userDto = userService.save(createUserDto);

        // Assert
        verify(customMapper).createUserDtoToUser(createUserDto);
        verify(userRepository).save(user);
        verify(customMapper).userToUserDto(user);
        assertNotNull(userDto);
    }

    @Test
    public void save_shouldReturnNullWhenUserRepositorySaveReturnsNull() {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setPassword("password");

        User user = new User();
        when(customMapper.createUserDtoToUser(createUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);

        // Act
        UserDto result = userService.save(createUserDto);

        // Assert
        verify(customMapper).createUserDtoToUser(createUserDto);
        verify(userRepository).save(user);
        verify(customMapper, never()).userToUserDto(user);
        assertNull(result);
    }

    @Test
    public void addRoleToUser_shouldSetRoleForExistingUser() {
        // Arrange
        String username = "testUser";
        UserRole roleName = UserRole.ADMIN;

        User user = new User();
        when(userRepository.findUserByUsername(username)).thenReturn(user);

        // Act
        userService.addRoleToUser(username, roleName);

        // Assert
        verify(userRepository).findUserByUsername(username);
        verify(userRepository).save(user);
        assertEquals(roleName, user.getRole());
    }

    @Test
    public void addRoleToUser_shouldNotSetRoleForNonExistingUser() {
        // Arrange
        String username = "nonExistingUser";
        UserRole roleName = UserRole.ADMIN;

        when(userRepository.findUserByUsername(username)).thenReturn(null);

        // Act
        userService.addRoleToUser(username, roleName);

        // Assert
        verify(userRepository).findUserByUsername(username);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void getUser_shouldReturnUserDtoForExistingUser() {
        // Arrange
        String username = "testUser";

        User user = new User();
        when(userRepository.findUserByUsername(username)).thenReturn(user);

        UserDto userDto = new UserDto();
        when(customMapper.userToUserDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUser(username);

        // Assert
        verify(userRepository).findUserByUsername(username);
        verify(customMapper).userToUserDto(user);
        assertEquals(userDto, result);
    }

    @Test
    public void getUser_shouldReturnNullForNonExistingUser() {
        // Arrange
        String username = "nonExistingUser";

        when(userRepository.findUserByUsername(username)).thenReturn(null);

        // Act
        UserDto result = userService.getUser(username);

        // Assert
        verify(userRepository).findUserByUsername(username);
        verify(customMapper, never()).userToUserDto(any(User.class));
        assertNull(result);
    }

    @Test
    public void getUsers_shouldReturnListOfUserDto() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        UserDto userDto1 = new UserDto();
        UserDto userDto2 = new UserDto();
        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2);

        when(customMapper.userListToUserDtoList(userList)).thenReturn(userDtoList);

        // Act
        List<UserDto> result = userService.getUsers();

        // Assert
        verify(userRepository).findAll();
        verify(customMapper).userListToUserDtoList(userList);
        assertEquals(userDtoList, result);
    }

    @Test
    public void register_shouldSaveNewUser() {
        // Arrange
        RegisterUserDto data = new RegisterUserDto("testUser","testUser","password",UserRole.USER);

        when(userRepository.findUserByUsername(data.username())).thenReturn(null);
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(),data.username(), encryptedPassword, data.role());
        when(userRepository.save(newUser)).thenReturn(newUser);

        // Act
        userService.register(data);

        // Assert
        verify(userRepository).findUserByUsername(data.username());
        verify(userRepository).save(newUser);
        assertNotNull(newUser.getPassword());
        // Check if password is encrypted
        assertTrue(new BCryptPasswordEncoder().matches("password", newUser.getPassword()));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void register_shouldThrowExceptionWhenUserAlreadyExists() {
        // Arrange
        RegisterUserDto data = new RegisterUserDto("existingUser","existingUser","password",UserRole.USER);

        when(userRepository.findUserByUsername(data.username())).thenReturn(new User());

        // Act
        userService.register(data);

        // Assert
        // Expecting UserAlreadyExistsException to be thrown
    }

    @Test
    public void loadUserByUsername_shouldReturnUserDetailsForExistingUser() {
        // Arrange
        String username = "testUser";

        User user = new User();
        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        UserDetails result = userService.loadUserByUsername(username);

        // Assert
        verify(userRepository).findByUsername(username);
        assertEquals(user, result);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_shouldThrowExceptionForNonExistingUser() {
        // Arrange
        String username = "nonExistingUser";

        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act
        userService.loadUserByUsername(username);

        // Assert
        // Expecting UsernameNotFoundException to be thrown
    }
    @Test
    public void findUserById_shouldReturnUserForExistingId() {
        // Arrange
        Long id = 1L;

        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findUserById(id);

        // Assert
        verify(userRepository).findById(id);
        assertEquals(user, result);
    }
}
