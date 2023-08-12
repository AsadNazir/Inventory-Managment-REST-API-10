package com.example.project.Resources;

import com.example.project.Auth;
import com.example.project.Domain.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import com.example.project.Services.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;



@Path("/")
public class InventoryResource {
    private final Logger logger = LoggerFactory.getLogger(InventoryResource.class);
    //1.
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response FetchById(@PathParam("id") int id, @Context ContainerRequestContext Rq) {

        setLogMessage("GettingItemByIDRequest", "GET");

        final String authHeader = Rq.getHeaderString("Authorization");
        boolean isAuthorized = Auth.authorize(Arrays.asList("admin", "user"), authHeader);
        if (isAuthorized) {
            try {
                InventoryService I = new InventoryService();
                return Response.status(Response.Status.OK).entity(new InventoryService().getRecordbyId(id)).build();
            } catch (Exception E) {
                return Response.status(Response.Status.BAD_REQUEST).entity(E.getMessage()).build();
            }
        } else {
            setLogMessage("Unauthorized Access", "GET");
            return Response.status(Response.Status.UNAUTHORIZED).entity("You are not Allowed to Perform this Operation").build();
        }

    }

    //2 .
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems(@Context ContainerRequestContext Rq) {

        logger.info("cuckoo");
        setLogMessage("GetALlItemsRequest", "GET");
        //Auth
        final String authHeader = Rq.getHeaderString("Authorization");
        boolean isAuthorized = Auth.authorize(Arrays.asList("admin", "user"), authHeader);
        if (isAuthorized) {
            try {
                InventoryService I = new InventoryService();
                return Response.status(Response.Status.OK).entity(I.getAll()).build();
            } catch (Exception E) {
                return Response.status(Response.Status.BAD_REQUEST).entity(E.getMessage()).build();
            }
        } else {
            setLogMessage("Unauthorized Access", "GET");
            return Response.status(Response.Status.UNAUTHORIZED).entity("You are not Allowed to Perform this Operation").build();

        }
    }

    //3.
    @GET
    @Path("/listByCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsByCategory(@QueryParam("category") int id, @Context ContainerRequestContext Rq) {
        setLogMessage("GetItemsByCategoryRequest", "GET");
        final String authHeader = Rq.getHeaderString("Authorization");
        boolean isAuthorized = Auth.authorize(Arrays.asList("admin", "user"), authHeader);
        if (isAuthorized) {
            try {
                InventoryService I = new InventoryService();
                return Response.status(Response.Status.OK).entity(new InventoryService().getItemsByCategory(id)).build();
            } catch (Exception E) {
                return Response.status(Response.Status.BAD_REQUEST).entity(E.getMessage()).build();
            }
        } else {
            setLogMessage("Unauthorized Access", "GET");
            return Response.status(Response.Status.UNAUTHORIZED).entity("You are not Allowed to Perform this Operation").build();
        }
    }

    //4.
    @GET
    @Path("/listByLocation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsByLocation(@QueryParam("location") int id, @Context ContainerRequestContext Rq) {

        setLogMessage("GetItemsByLocationRequest", "GET");
        final String authHeader = Rq.getHeaderString("Authorization");
        boolean isAuthorized = Auth.authorize(Arrays.asList("admin", "user"), authHeader);
        if (isAuthorized) {
            try {
                InventoryService I = new InventoryService();
                return Response.status(Response.Status.OK).entity(new InventoryService().getItemsByLocation(id)).build();
            } catch (Exception E) {
                return Response.status(Response.Status.BAD_REQUEST).entity(E.getMessage()).build();
            }
        } else {
            setLogMessage("Unauthorized Access", "GET");
            return Response.status(Response.Status.UNAUTHORIZED).entity("You are not Allowed to Perform this Operation").build();
        }
    }


    //5.
    @GET
    @Path("/lists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsByLocationAndCategory(@QueryParam("location") int location, @QueryParam("category") int category, @Context ContainerRequestContext Rq) {

        setLogMessage("GetItemsByLocationAndCategoryRequest", "GET");
        final String authHeader = Rq.getHeaderString("Authorization");
        boolean isAuthorized = Auth.authorize(Arrays.asList("admin", "user"), authHeader);
        if (isAuthorized) {
            try {
                InventoryService I = new InventoryService();
                return Response.status(Response.Status.OK).entity(I.getAll()).build();
            } catch (Exception E) {
                return Response.status(Response.Status.BAD_REQUEST).entity(E.getMessage()).build();
            }
        } else {
            setLogMessage("Unauthorized Access", "GET");
            return Response.status(Response.Status.UNAUTHORIZED).entity("You are not Allowed to Perform this Operation").build();
        }

    }

    //6.
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddNewInventoryItem(String jsonData, @Context ContainerRequestContext Rq) {

        setLogMessage("AddItemRequest", "POST");
        final String authHeader = Rq.getHeaderString("Authorization");
        boolean isAuthorized = Auth.authorize(Arrays.asList("admin"), authHeader);

        if (isAuthorized) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Item newItem = objectMapper.readValue(jsonData, Item.class);

                return Response.status(Response.Status.OK).entity(new InventoryService().InsertItem(newItem)).build();

            } catch (Exception E) {
                return Response.status(Response.Status.BAD_REQUEST).entity(E.getMessage()).build();
            }
        } else {
            setLogMessage("Unauthorized Access", "POST");
            return Response.status(Response.Status.UNAUTHORIZED).entity("You are not Allowed to Perform this Operation").build();
        }
    }


    //7.
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response UpdateInventoryItem(String jsonData, @PathParam("id") int id, @Context ContainerRequestContext Rq) {
        final String authHeader = Rq.getHeaderString("Authorization");
        boolean isAuthorized = Auth.authorize(Arrays.asList("admin"), authHeader);

        setLogMessage("UpdateItemRequest", "PUT");
        if (isAuthorized) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Item newItem = objectMapper.readValue(jsonData, Item.class);
                return Response.status(Response.Status.OK).entity(new InventoryService().updateItem(id, newItem)).build();

            } catch (Exception E) {
                return Response.status(Response.Status.BAD_REQUEST).entity(E.getMessage()).build();
            }
        } else
        {
            setLogMessage("Unauthorized Access", "PUT");
            return Response.status(Response.Status.UNAUTHORIZED).entity("You are not Allowed to Perform this Operation").build();
        }
    }

    //8.
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response DeleteInventoryItem(@PathParam("id") int id, @Context ContainerRequestContext Rq) {

        setLogMessage("DeleteItemRequest", "DELETE");
        final String authHeader = Rq.getHeaderString("Authorization");
        boolean isAuthorized = Auth.authorize(Arrays.asList("admin"), authHeader);

        if (isAuthorized) {
            try {
                return Response.status(Response.Status.OK).entity(new InventoryService().deleteItem(id)).build();

            } catch (Exception E) {
                return Response.status(Response.Status.BAD_REQUEST).entity(E.getMessage()).build();
            }

        } else {
            setLogMessage("Unauthorized Access", "DELETE");
            return Response.status(Response.Status.UNAUTHORIZED).entity("You are not Allowed to Perform this Operation").build();
        }
    }

    private void setLogMessage(String Msg, String type) {
        //Logger



        logger.info(new Date().toString() + "_" + type + "_" + Msg);
    }


}