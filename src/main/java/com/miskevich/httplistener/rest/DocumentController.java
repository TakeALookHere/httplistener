package com.miskevich.httplistener.rest;

import com.miskevich.httplistener.model.Document;
import com.miskevich.httplistener.service.DocumentService;
import com.miskevich.httplistener.util.JsonConverter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/document")
@Produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DocumentController {

    private DocumentService documentService = new DocumentService();

    @POST
    @Path("/add")
    public Response add(@RequestBody String documentFromJson) throws InterruptedException {
        Document document = JsonConverter.fromJson(documentFromJson, Document.class);
        Optional<Document> optional = documentService.add(document);
        if (optional.isPresent()) {
            Document addedDocument = optional.get();
            String json = JsonConverter.toJson(addedDocument);
            return Response.ok(json, MediaType.APPLICATION_JSON_UTF8_VALUE).build();
        }
        return Response.status(400).build();
    }
}
