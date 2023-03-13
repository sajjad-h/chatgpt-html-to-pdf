package com.app.chatgpthtmltopdf.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextExtractor {
    private static String UPLOAD_DIR_LOCATION = "upload-dir";

    public static List<String> getTexts(String fileName) {

        Path rootLocation = Paths.get(UPLOAD_DIR_LOCATION);
        Path htmlFilePath = rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
        

        List<String> texts = new ArrayList<String>();
        File inputFile = new File(htmlFilePath.toString());
        Document doc;
        try {
            doc = Jsoup.parse(inputFile, "UTF-8");
            Elements elems = doc.select(".whitespace-pre-wrap");
            System.out.println(elems.size());
            for (Element element : elems) {
                System.out.println(element.text());
                texts.add(element.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texts;
    }
}
