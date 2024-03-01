package com.distribuida.authors.rest;

import com.distribuida.authors.clients.AuthorRestClient;
import com.distribuida.authors.db.Book;
import com.distribuida.authors.dtos.BookDto;
import com.distribuida.authors.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Path("/books")
@Produces (MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped
public class BooksRest {

    @Inject
    BookRepository repo;

    @Inject
    @RestClient
    AuthorRestClient authorClient;

    @GET
    @Operation(summary = "GET ALL", description = "Obtener todos los libros")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Libros retornados"),
                    @APIResponse(responseCode = "404", description = "Libros no retornados")
            }
    )
    public List<BookDto> findAll(){
        return repo.findAll().stream()
                .map(book->{
                    var author = authorClient.getById(book.getAuthorId());

                    var dto = BookDto.from(book);

                    String aname = String.format("%s %s",
                            author.getLastName(), author.getFirstName());

                    dto.setAuthorName( aname );

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GET
    @Operation(summary = "GET ", description = "Obtener libro")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Libro retornado"),
                    @APIResponse(responseCode = "404", description = "Libro no retornado")
            }
    )
    @Path("/{id}")
    public Response getById(@PathParam("id")Integer id){
        var book = repo.findById(id);

        if(book==null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();

    }

    @POST
    @Operation(summary = "POST ", description = "Crear libro")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Libro creado"),
                    @APIResponse(responseCode = "404", description = "Libro no creado")
            }
    )
    public Response create(Book p){
        repo.create(p);
        return Response.status(Response.Status.CREATED.getStatusCode(), "Libro creado").build();
    }

    @PUT
    @Operation(summary = "PUT ", description = "Actualizar libro")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Libro actualizado"),
                    @APIResponse(responseCode = "404", description = "Libro no actualizado")
            }
    )
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Book bookObj){
       Book book=repo.findById(id);
       book.setIsbn(bookObj.getIsbn());
       book.setPrice(bookObj.getPrice());
       book.setTitle(book.getTitle());

       return Response.ok().build();
    }

    @DELETE
    @Operation(summary = "DELETE ", description = "Eliminar libro")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Libro eliminado"),
                    @APIResponse(responseCode = "404", description = "Libro no eliminado")
            }
    )
    @Path("/{id}")
    public Response delete( @PathParam("id") Integer id){
        repo.delete(id);
        return Response.ok().build();

    }
}
