package com.app.chatgpthtmltopdf.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {
    private static String RESULT_DIR_LOCATION = "result-dir";
    private static String PDF_EXTENSION = ".pdf";


    public static void generatePdf(List<String> texts, String fileName) {
        Document doc = new Document();
        Path rootLocation = Paths.get(RESULT_DIR_LOCATION);
        String justFileName = FileNameUtils.getJustFileName(fileName);
        String pdfFileName = justFileName + PDF_EXTENSION;
        Path destinationFile = rootLocation.resolve(Paths.get(pdfFileName)).normalize().toAbsolutePath();
        try {
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new Exception("Cannot store file outside current directory.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(destinationFile.toString()));
            doc.open();
            for (String text : texts) {
                Paragraph paragraph = new Paragraph(text);
                doc.add(paragraph);
            }
            doc.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
