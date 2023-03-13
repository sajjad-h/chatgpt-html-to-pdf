package com.app.chatgpthtmltopdf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("chatgpt-html-to-pdf")
    public String main() {
        return "home";
    }
}