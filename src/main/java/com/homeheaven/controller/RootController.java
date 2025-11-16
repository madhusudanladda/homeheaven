package com.homeheaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    // Redirect root to the login page (static resource)
    @GetMapping("/")
    public String root() {
        return "redirect:/login.html";
    }
}
