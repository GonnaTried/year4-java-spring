package com.bunnet.year4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/index")
    public String redirectToIndex() {
        return "forward:/Admin/index.html";
    }

    @GetMapping("/cv")
    public String redirectToCV() {
        return "forward:/Admin/cv.html";
    }

}
