package com.app.chatgpthtmltopdf.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.chatgpthtmltopdf.Services.FileUploadService;
import com.app.chatgpthtmltopdf.utils.PDFGenerator;
import com.app.chatgpthtmltopdf.utils.TextExtractor;

@Controller
public class FileUploadController {
    
    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        String uploadedFileName = fileUploadService.store(file);
        System.out.println(uploadedFileName);
        
        List<String> texts = TextExtractor.getTexts(uploadedFileName);
        System.out.println(texts);

        PDFGenerator.generatePdf(texts, uploadedFileName);

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());

        return "redirect:/chatgpt-html-to-pdf";
    }

    

}
