package org.acme;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

@Path("/users")
public class UserResource {

    @Inject
    UserPdfService pdfService;

    @GET
    @Path("/{id}/pdf")
    @Produces("application/pdf")
    public Response getUserPdf(@PathParam("id") Long id) {
        PdfUser user = PdfUser.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        byte[] pdfBytes = pdfService.generateUserPdf(user);
        return Response.ok(pdfBytes)
                .header("Content-Disposition", "inline; filename=\"user-" + id + ".pdf\"")
                .build();
    }

    @GET
    @Path("/{id}/pdf-stream")
    @Produces("application/pdf")
    public Response getUserPdfStream(@PathParam("id") Long id) {
        PdfUser user = PdfUser.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return Response.ok((StreamingOutput) output -> {
                    Document doc = new Document();
                    PdfWriter.getInstance(doc, output);
                    doc.open();
                    // logo
                    Image logo = Image.getInstance(getClass().getResource("/logo.png"));
                    logo.scaleToFit(100, 100);
                    doc.add(logo);
                    doc.add(new Paragraph("User: " + user.firstName + " " + user.lastName));

                    // table

                    PdfPTable table = new PdfPTable(2);
                    table.addCell("First Name");
                    table.addCell(user.firstName);
                    table.addCell("Last Name");
                    table.addCell(user.lastName);
                    doc.add(table);

                    doc.close();
                }).header("Content-Disposition", "inline; filename=\"user-" + id + ".pdf\"")
                .build();
    }
}
