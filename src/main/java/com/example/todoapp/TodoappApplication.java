package com.example.todoapp;

import com.example.todoapp.dto.user.CreateUserDto;
import com.example.todoapp.entity.UserRole;
import com.example.todoapp.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
public class TodoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(UserService userService){
		return args -> {

			userService.save(CreateUserDto.builder().name("admin").username("admin").password("admin").userRole(UserRole.USER).build());

			var user= userService.getUser("admin");
	};
}
}
