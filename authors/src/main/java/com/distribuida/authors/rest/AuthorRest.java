package com.distribuida.authors.rest;


import com.distribuida.authors.db.Author;
import com.distribuida.authors.repo.AuthorRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class AuthorRest {

    @Inject
    AuthorRepository authorRepository;


    @GET
    @Operation(summary = "Obtener autores",
            description = "Obtiene todos los autores")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Autores listados"),
                    @APIResponse(responseCode = "404", description = "Autores no listados")
            }
    )
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener author ",
            description = "Obtiene un author por id")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Autor listado"),
                    @APIResponse(responseCode = "404", description = "Autor no listado")
            }
    )
    public Response getById( @PathParam("id") Integer id){
        var author=authorRepository.findById(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(author).build();
    }

    @POST
    @Operation(summary = "POST",
            description = "Crear autor")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Autor creado"),
                    @APIResponse(responseCode = "404", description = "Autor no creado")
            }
    )
    public Response create(Author a){
        authorRepository.create(a);
        return Response.status(Response.Status.CREATED.getStatusCode(), "Author creado").build();

    }

    @PUT
    @Operation(summary = "PUT",
            description = "Actualizar autor")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Autor acutalizado"),
                    @APIResponse(responseCode = "404", description = "Autor no actualizado")
            }
    )
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Author obj){
        Author author= authorRepository.findById(id);
        author.setFirstName(obj.getFirstName());
        author.setLastName(obj.getFirstName());

        return Response.ok().build();
    }


    @DELETE
    @Operation(summary = "DELETE",
            description = "Eliminar autor")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Autor eliminado"),
                    @APIResponse(responseCode = "404", description = "Autor no eliminado")
            }
    )
    @Path("/{id}")
    public Response delete(@PathParam("id")Integer id){
        authorRepository.delete(id);

        return Response.ok().build();
    }

}
