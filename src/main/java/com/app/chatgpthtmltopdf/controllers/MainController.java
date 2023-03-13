package com.app.chatgpthtmltopdf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("chatgpt-html-to-pdf")
    public String main(@RequestParam(value = "filename", required = false, defaultValue = "") String fileName, Model model) {
        if (!fileName.isEmpty()) {
            String pdfURL = "/files/" + fileName;
            model.addAttribute("pdf_url", pdfURL);
        }
        return "home";
    }

}