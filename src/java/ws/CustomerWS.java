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
import entity.Customer;
import rn.CustomerRN;

/**
 * REST Web Service
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
@Path("customers")
public class CustomerWS {

    private CustomerRN customerRn = new CustomerRN();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookWS
     */
    public CustomerWS() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers() {
        return customerRn.list();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getByCode(@PathParam("id") int id) {
        return customerRn.findById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer addBook(Customer customer, @Context final HttpServletResponse response) {
        try {
            customerRn.insert(customer);
            // generates the 201 header response
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.flushBuffer();
            //gets the book for response in json
            return customerRn.findById(customer.getId());
        } catch(Exception e) {
            // throw 500 error
            throw new InternalServerErrorException();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer update(@PathParam("id") int id, Customer customer, @Context final HttpServletResponse response) {
        try {
            Customer updated = customerRn.findById(id);
            updated.setName(customer.getName());
            updated.setPhone(customer.getPhone());
            customerRn.update(updated);
            return updated;
        } catch(Exception e) {
            // throw 500 error
            throw new InternalServerErrorException();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> remove(@PathParam("id") int id, @Context final HttpServletResponse response) {
        // gets the book
        Customer b = customerRn.findById(id);
        if (b == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        
        try {
            response.flushBuffer();
            customerRn.remove(b.getId());
            response.setStatus(HttpServletResponse.SC_OK);
            return customerRn.list();
            
        } catch(IOException e) {
            // throw 500 error
            throw new InternalServerErrorException();
        }
    }
}
