package com.bunnet.year4.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "page/user";
    }

    @GetMapping("/login")
    public String loginPage(Principal principal, Model model) {
        return "auth/login";
    }

}
