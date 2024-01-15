package com.example.todoapp;

import com.example.todoapp.dto.role.CreateRoleDto;
import com.example.todoapp.dto.user.CreateUserDto;
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
			userService.save(CreateRoleDto.builder().name("ROLE_USER").build());
			userService.save(CreateRoleDto.builder().name("ROLE_ADMIN").build());
			userService.save(CreateUserDto.builder().name("admin").username("admin").password("admin").roleDtos(new HashSet<>()).build());
			userService.addRoleToUser("admin", "ROLE_ADMIN");
			//userService.addRoleToUser("admin", "ROLE_USER");
	};
}
  @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
