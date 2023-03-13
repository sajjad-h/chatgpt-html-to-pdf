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


    public static String generatePdf(List<String> texts, String fileName) {
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
            int times = 0;
            for (String text : texts) {
                String p = "Me: ";
                if (times % 2 == 1) {
                    p = "ChatGPT: ";
                }
                Paragraph paragraph = new Paragraph(p + text);
                doc.add(paragraph);
                if (times % 2 == 1) {
                    doc.add(new Paragraph(" "));
                }
                times++;
            }
            doc.close();
            writer.close();
            return pdfFileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
