package com.distribuida.authors.rest;


import com.distribuida.authors.db.Author;
import com.distribuida.authors.repo.AuthorRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById( @PathParam("id") Integer id){
        var author=authorRepository.findById(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(author).build();
    }

    @POST
    public Response create(Author a){
        authorRepository.create(a);
        return Response.status(Response.Status.CREATED.getStatusCode(), "Author creado").build();

    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Author obj){
        Author author= authorRepository.findById(id);
        author.setFirstName(obj.getFirstName());
        author.setLastName(obj.getFirstName());

        return Response.ok().build();
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id")Integer id){
        authorRepository.delete(id);

        return Response.ok().build();
    }

}
