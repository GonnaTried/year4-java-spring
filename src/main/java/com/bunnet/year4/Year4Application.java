package com.bunnet.year4;

import com.bunnet.year4.model.User;
import com.bunnet.year4.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Year4Application {

	public static void main(String[] args) {
		SpringApplication.run(Year4Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("12345678")); // HASH the password
				admin.setRoles("ROLE_ADMIN");
				userRepository.save(admin);
				System.out.println("Created ADMIN user.");
			}

			if (userRepository.findByUsername("user").isEmpty()) {
				User regularUser = new User();
				regularUser.setUsername("user");
				regularUser.setPassword(passwordEncoder.encode("123")); // HASH the password
				regularUser.setRoles("ROLE_USER");
				userRepository.save(regularUser);
				System.out.println("Created USER.");
			}
		};
	}

}
