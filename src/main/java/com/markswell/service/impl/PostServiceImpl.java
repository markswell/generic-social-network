package com.markswell.service.impl;

import com.markswell.domain.User;
import com.markswell.service.FollowerService;
import com.markswell.service.PostService;
import com.markswell.service.UserService;
import jakarta.inject.Inject;
import com.markswell.domain.Post;
import com.markswell.dto.PostRequest;
import com.markswell.dto.PostResponse;
import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import com.markswell.repository.PostRepository;
import com.markswell.exception.NotFollowException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PostServiceImpl implements PostService {

    private UserService userService;

    private PostRepository postRepository;

    private FollowerService followerService;

    @Inject
    public PostServiceImpl(UserService userService,
                           PostRepository postRepository,
                           FollowerService followerService) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.followerService = followerService;
    }


    @Override
    @Transactional
    public Post savePost(Long id, PostRequest postRequest) {
        var user = userService.findById(id);

        Post post = new Post();
        post.setText(postRequest.getText());
        post.setUser(user);

        postRepository.persist(post);

        return post;
    }

    @Override
    public List<PostResponse> getPosts(Long userId,
                                       Long followerId) {
        var user = userService.findById(userId);
        var follower = getFollowerById(followerId);

        if(!followerService.isFollower(user, follower)) {
            throw new NotFollowException("User %d is not followed by %d user".formatted(userId, followerId));
        }


        return postRepository.find("user", Sort.by("dateTime", Sort.Direction.Descending) , user)
                .list()
                .stream()
                .map(PostResponse::fromEntity)
                .toList();
    }

    private User getFollowerById(Long followerId) {
        try {
            return userService.findById(followerId);
        } catch(Exception e) {
            throw new NotFollowException("Follower don't exist");
        }
    }

}
