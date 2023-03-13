package com.app.chatgpthtmltopdf.Services;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String store(MultipartFile file);
}
