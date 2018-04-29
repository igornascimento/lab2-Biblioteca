/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import entity.Movimentation;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import rn.MovimentationRN;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
@Path("/movimentations")
public class MovimentationWS {

    MovimentationRN movRn = new MovimentationRN();
    
    @Context
    private UriInfo context;

    public MovimentationWS() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movimentation> list() {
        return movRn.list();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Movimentation add(Movimentation mov, @Context final HttpServletResponse response) {
        try {
            movRn.insert(mov);
            // generates the 201 header response
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.flushBuffer();
            //gets the book for response in json
            return movRn.findById(mov.getId());
        } catch(Exception e) {
            // throw 500 error
            throw new InternalServerErrorException();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Movimentation update(@PathParam("id") int id, Movimentation mov, @Context final HttpServletResponse response) {
        try {
            Movimentation updated = movRn.findById(id);
            updated.setCustomer(mov.getCustomer());
            updated.setBookList(mov.getBookList());
            updated.setDate(mov.getDate());
            movRn.update(updated);
            return updated;
        } catch(Exception e) {
            // throw 500 error
            throw new InternalServerErrorException();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movimentation> remove(@PathParam("id") int id, @Context final HttpServletResponse response) {
        // gets the book
        Movimentation m = movRn.findById(id);
        if (m == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
        try {
            response.flushBuffer();
            movRn.remove(m.getId());
            response.setStatus(HttpServletResponse.SC_OK);
            return movRn.list();
            
        } catch(IOException e) {
            // throw 500 error
            throw new InternalServerErrorException();
        }
    }
    
}
