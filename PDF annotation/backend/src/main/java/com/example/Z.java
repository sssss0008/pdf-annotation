package com.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Service;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class Z {

    private byte[] lastAnnotatedPdf;

    public byte[] annotatePdf(W request) throws Exception {
        if (request.getOriginalPdf() == null || request.getAnnotations() == null || request.getAnnotations().isEmpty()) {
            throw new IllegalArgumentException("Invalid request");
        }
        byte[] pdfBytes = Base64.getDecoder().decode(request.getOriginalPdf().split(",")[1]);
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            for (W.D annotation : request.getAnnotations()) {
                PDPage page = document.getPage(annotation.getPage() - 1);
                
                PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                Color color = Color.decode(annotation.getColor());
                contentStream.setNonStrokingColor(color);
                contentStream.setTextMatrix(Matrix.getTranslateInstance(annotation.getX(), page.getMediaBox().getHeight() - annotation.getY() - annotation.getHeight()));
                contentStream.showText(annotation.getText());
                contentStream.endText();
                contentStream.close();

                PDAnnotationLink link = new PDAnnotationLink();
                PDRectangle rect = new PDRectangle(annotation.getX(), page.getMediaBox().getHeight() - annotation.getY() - annotation.getHeight(), annotation.getWidth(), annotation.getHeight());
                link.setRectangle(rect);
                PDActionURI action = new PDActionURI();
                action.setURI(annotation.getLink());
                link.setAction(action);
                page.getAnnotations().add(link);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            lastAnnotatedPdf = out.toByteArray();
            return lastAnnotatedPdf;
        }
    }

    public byte[] getLastAnnotatedPdf() {
        return lastAnnotatedPdf;
    }
}