package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

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
}
