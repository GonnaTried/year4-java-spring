package com.bunnet.year4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint, accessible to everyone!";
    }

    @GetMapping("/user/profile")
    public String userProfile() {
        return "This is the user profile. Accessible by USER and ADMIN.";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "This is the admin dashboard. Accessible ONLY by ADMIN.";
    }
}
