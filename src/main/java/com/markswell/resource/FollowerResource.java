package com.markswell.resource;

import jakarta.ws.rs.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import com.markswell.dto.FollowerRequest;
import com.markswell.service.FollowerService;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users/{userId}/followers")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class FollowerResource {

    @Inject
    private FollowerService followerService;

    @GET
    public Response listFollowers(@PathParam("userId") Long userId) {
        var followers = followerService.findFollowers(userId);
        return Response.ok(followers).build();
    }

    @PUT
    public Response followUser(@PathParam("userId") Long id,
                               FollowerRequest followerRequest) {
        if(id.equals(followerRequest.getFollowerId())) {
            return Response.status(CONFLICT)
                    .entity("You can't follow yourself")
                    .build();
        }
        followerService.followUser(id, followerRequest);
        return Response.status(CREATED).build();
    }

    @DELETE
    @Path("/{followerId}")
    public Response unfollow(@PathParam("userId") Long userId,
                             @PathParam("followerId") Long followerId){
        return followerService.unfollow(userId, followerId) ?
                Response.status(NO_CONTENT).build() :
                Response.status(NOT_FOUND).build();
    }

}
