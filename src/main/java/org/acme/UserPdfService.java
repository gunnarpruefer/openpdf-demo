package org.acme;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.ByteArrayOutputStream;

@ApplicationScoped
public class UserPdfService {

    public byte[] generateUserPdf(PdfUser user) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("User Record", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("ID: " + user.id, normalFont));
            document.add(new Paragraph("First Name: " + user.firstName, normalFont));
            document.add(new Paragraph("Last Name: " + user.lastName, normalFont));
            document.add(new Paragraph("Email: " + user.email, normalFont));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }

    }
}
