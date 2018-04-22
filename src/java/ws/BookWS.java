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
//import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import entity.Book;
import rn.BookRN;

/**
 * REST Web Service
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
@Path("books")
public class BookWS {

    private BookRN bookRn = new BookRN();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookWS
     */
    public BookWS() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return bookRn.list();
    }
    
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getByCode(@PathParam("code") String code) {
        return bookRn.findByCode(code);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book addBook(Book book, @Context final HttpServletResponse response) throws Exception {
        // adds the book
        bookRn.insert(book);
        // generates the 201 header response
        response.setStatus(HttpServletResponse.SC_CREATED);
        
        try {
            response.flushBuffer();
            //gets the book for response in json
            return bookRn.findByCode(book.getCode());
        } catch(IOException e) {
            // throw 500 error
            throw new InternalError();
        }
    }
    
    @DELETE
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> remove(@PathParam("code") String code, @Context final HttpServletResponse response) {
        // gets the book
        Book b = bookRn.findByCode(code);
        if (b == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
        try {
            response.flushBuffer();
            bookRn.remove(b.getCode());
            response.setStatus(HttpServletResponse.SC_OK);
            return bookRn.list();
            
        } catch(IOException e) {
            // throw 500 error
            throw new InternalError();
        }
        
        
    }
}
