package com.app.chatgpthtmltopdf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.chatgpthtmltopdf.Services.FileUploadService;

@Controller
public class FileUploadController {
    
    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        String uploadedFilePath = fileUploadService.store(file);
        System.out.println(uploadedFilePath);

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());

        return "redirect:/chatgpt-html-to-pdf";
    }

}
