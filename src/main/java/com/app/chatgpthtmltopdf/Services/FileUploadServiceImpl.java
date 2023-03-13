package com.app.chatgpthtmltopdf.Services;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final Path rootLocation;
    private static String UPLOAD_DIR_LOCATION = "upload-dir";

    @Autowired
    public FileUploadServiceImpl() {
        this.rootLocation = Paths.get(UPLOAD_DIR_LOCATION);
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file!");
            }
            String originalFileName = file.getOriginalFilename();
            String extention = getExtension(originalFileName);
            String justFileName = getJustFileName(originalFileName);
            String fileName = justFileName + "_" + randomString() + "." + extention;
            Path destinationFile = this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new Exception("Cannot store file outside current directory.");
            }
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            return destinationFile.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String randomString() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;
    }

    public String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        return extension;
    }

    public String getJustFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String justFileName = fileName.substring(0, index);
        return justFileName;
    }
}
