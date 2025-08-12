// src/main/java/com/bunnet/year4/controller/ViewController.java

package com.bunnet.year4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ViewController {

    @GetMapping({"/", "/home"})
    public String home() {
        // Returns home.html
        return "index";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "test/apiUser";
    }

    @GetMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "test/apiAdmin";
    }
}