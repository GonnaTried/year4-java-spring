package com.bunnet.year4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bunnet.year4.dto.user.UserRegistrationDto;
import com.bunnet.year4.model.User;
import com.bunnet.year4.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // get user by id use Optional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void register(UserRegistrationDto registrationDto) throws Exception {
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new Exception("User with username " + registrationDto.getUsername() + " already exists.");
        }

        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        newUser.setRoles("ROLE_USER");

        userRepository.save(newUser);
    }
}
