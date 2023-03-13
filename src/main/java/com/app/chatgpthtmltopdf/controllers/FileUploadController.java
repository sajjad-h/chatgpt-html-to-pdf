package com.app.chatgpthtmltopdf.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.chatgpthtmltopdf.services.StorageService;
import com.app.chatgpthtmltopdf.utils.PDFGenerator;
import com.app.chatgpthtmltopdf.utils.TextExtractor;

@Controller
public class FileUploadController {
    
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        String uploadedFileName = storageService.store(file);
        System.out.println(uploadedFileName);
        
        List<String> texts = TextExtractor.getTexts(uploadedFileName);
        System.out.println(texts);

        String pdfFileName = PDFGenerator.generatePdf(texts, uploadedFileName);



        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename());

        return "redirect:/chatgpt-html-to-pdf?filename=" + pdfFileName;
    }

    @GetMapping("/files/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"").contentType(MediaType.parseMediaType("application/pdf")).body(file);
    }

}
