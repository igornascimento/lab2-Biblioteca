/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import entity.Author;
import rn.AuthorRN;

/**
 * REST Web Service
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
@Path("authors")
public class AuthorWS {

    private AuthorRN authorRn = new AuthorRN();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookWS
     */
    public AuthorWS() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() {
        return authorRn.list();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author getByCode(@PathParam("id") int id) {
        return authorRn.findById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Author addBook(Author author, @Context final HttpServletResponse response) {
        try {
            authorRn.insert(author);
            // generates the 201 header response
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.flushBuffer();
            //gets the author for response in json
            return authorRn.findById(author.getId());
        } catch(Exception e) {
            // throw 500 error
            throw new InternalServerErrorException();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> remove(@PathParam("id") int id, @Context final HttpServletResponse response) {
        // gets the book
        Author a = authorRn.findById(id);
        if (a == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
        try {
            response.flushBuffer();
            authorRn.remove(a.getId());
            response.setStatus(HttpServletResponse.SC_OK);
            return authorRn.list();
            
        } catch(IOException e) {
            // throw 500 error
            throw new InternalServerErrorException();
        }
        
        
    }
}
