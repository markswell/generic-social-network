package com.markswell.resource;

import jakarta.ws.rs.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import com.markswell.domain.User;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import com.markswell.dto.UserRequest;
import com.markswell.service.UserService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static java.util.Objects.isNull;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    public Response listAll() {
        List<User> users = userService.listAll();
        return Response.ok(users).build();
    }

    @POST
    public Response createUser(@Valid UserRequest userRequest) {
        var user = userService.createUser(userRequest);
        return Response.status(CREATED).entity(user).build();
    }

    @PUT
    @Path("{userId}")
    public Response update(@PathParam("userId") Long userId, @Valid UserRequest userRequest) {
        User user = userService.update(userId, userRequest);
        return !isNull(user) ?
                Response.ok(user).build() :
                Response.status(NOT_FOUND.getStatusCode()).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = userService.delete(id);
        return deleted ?
                Response.status(204).build() :
                Response.status(NOT_FOUND.getStatusCode()).build();
    }

}
