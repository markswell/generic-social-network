package com.markswell.resource;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;
import com.markswell.domain.Post;
import jakarta.ws.rs.core.Response;
import com.markswell.dto.PostRequest;
import com.markswell.dto.PostResponse;
import com.markswell.service.PostService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users/{userId}/posts")
@Consumes(APPLICATION_JSON)
public class PostResource {

    @Inject
    private PostService postService;

    @POST
    public Response savePost(@PathParam("userId") Long id, PostRequest postRequest) {
        Post post = postService.savePost(id, postRequest);
        return Response.status(CREATED).entity(post).build();
    }

    @GET
    public Response getPosts(@PathParam("userId") @NotNull Long userId,
                             @HeaderParam("FollowerId")
                             @NotNull(message = "The header FollowerId must not be null")
                             Long followerId) {
        List<PostResponse> posts = postService.getPosts(userId, followerId);
        return Response.ok(posts).build();
    }

}
