package com.snailtraveller.learningspringboot.clientproxy;

import com.snailtraveller.learningspringboot.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public interface UserResourceV1 {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<User> fetchUsers(@QueryParam("gender") String gender);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    User fetchUser(@PathParam("userUid") UUID userUid);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void insertNewUser(User user);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void updateUser(User user);

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    void deleteUser(@PathParam("userUid") UUID userUid);

}
