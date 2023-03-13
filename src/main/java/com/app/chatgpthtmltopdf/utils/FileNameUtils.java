package com.app.chatgpthtmltopdf.utils;

public class FileNameUtils {
    
    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        return extension;
    }

    public static String getJustFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String justFileName = fileName.substring(0, index);
        return justFileName;
    }
}
