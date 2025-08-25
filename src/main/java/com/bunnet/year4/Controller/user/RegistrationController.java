package com.bunnet.year4.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bunnet.year4.dto.user.UserRegistrationDto;
import com.bunnet.year4.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    // This ensures a new DTO is always available in the model for the form
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "auth/register"; // Return the name of the register.html template
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto registrationDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.register(registrationDto);
        } catch (Exception e) {
            result.rejectValue("username", null, "An account with that username already exists.");
            return "auth/register";
        }

        return "redirect:/login?success";
    }
}
