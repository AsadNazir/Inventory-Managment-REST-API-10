package com.example.project.Resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import com.example.project.Services.*;
import jakarta.ws.rs.core.Response;

import javax.print.attribute.standard.Media;

@Path("/")
public class InventoryResource {

    //1.
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response FetchById(@PathParam("id") int id) {

        return Response.status(Response.Status.OK).entity(new InventoryService().getRecordbyId(id)).build();
        //return new InventoryService().getRecordbyId(id);
    }

    //2 .
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllItems() {
        InventoryService I = new InventoryService();
        return I.getAll();
    }

    //3.
    @GET
    @Path("/listByCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemsByCategory(@QueryParam("category") int id ) {
        return new InventoryService().getItemsByCategory(id);
    }

    //4.
    @GET
    @Path("/lists")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemsByLocationAndCategory(@QueryParam("location") int location , @QueryParam("category") int category) {
        return new InventoryService().getItemByLocationAndCategory(location,category);
    }


}