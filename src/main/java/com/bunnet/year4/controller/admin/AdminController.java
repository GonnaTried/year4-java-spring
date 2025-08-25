package com.bunnet.year4.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bunnet.year4.dto.user.UserRegistrationDto;
import com.bunnet.year4.model.User;
import com.bunnet.year4.repository.UserRepository;
import com.bunnet.year4.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String adminPanel(Model model) {

        return "admin/adminPanel";
    }

    @GetMapping("/user")
    public String manageUser(Model model) {
        List<User> allUsers = userService.getAllUsers();

        List<User> notAdminUsers = allUsers.stream()
                .filter(user -> !user.getRoles().contains("ADMIN"))
                .toList();

        model.addAttribute("users", notAdminUsers);
        return "admin/user";
    }

    @GetMapping("/user/add")
    public String showAddUser(Model model) {
        model.addAttribute("addUserForm", new UserRegistrationDto());
        return "admin/addUser";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute("addUserForm") UserRegistrationDto userRegistrationDto, RedirectAttributes redirectAttributes) {
        try {
            if (userRegistrationDto.getUsername() == null || !userRegistrationDto.getUsername().isEmpty()) {
                if (userRepository.findAll().stream().noneMatch(user -> user.getUsername().equals(userRegistrationDto.getUsername()))) {
                    if (userRegistrationDto.getPassword() != null && !userRegistrationDto.getPassword().isEmpty()) {

                        userService.register(userRegistrationDto);
                        redirectAttributes.addFlashAttribute("successMessage", "added ' " + userRegistrationDto.getUsername() + " ' successfully");
                        return "redirect:/admin/user/add";
                    } else {
                        redirectAttributes.addFlashAttribute("errorMessage", "Password cannot be empty");
                        return "redirect:/admin/user/add";
                    }
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Username Already Exists");
                    return "redirect:/admin/user/add";
                }

            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Username cannot be empty");
                return "redirect:/admin/user/add";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/admin/user/add";
        }

    }

}
