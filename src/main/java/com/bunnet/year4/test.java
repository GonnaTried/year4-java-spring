package com.bunnet.year4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/home")
    public String home() {
        return new String("Korn Bunnet");
    }

}
