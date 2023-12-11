package com.markswell.service.impl;

import jakarta.inject.Inject;
import com.markswell.domain.User;
import com.markswell.domain.Follower;
import jakarta.transaction.Transactional;
import com.markswell.dto.FollowerRequest;
import com.markswell.dto.FollowerResponse;
import com.markswell.service.FollowerService;
import com.markswell.dto.FollowerPerUserResposnse;
import jakarta.persistence.EntityNotFoundException;
import com.markswell.repository.FollowerRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.ArrayList;

import static java.lang.Boolean.*;

@ApplicationScoped
public class FollowerServiceImpl implements FollowerService {

    private UserServiceImpl userService;

    private FollowerRepository followerRepository;

    @Inject
    public FollowerServiceImpl(UserServiceImpl userService, FollowerRepository followerRepository) {
        this.userService = userService;
        this.followerRepository = followerRepository;
    }

    @Override
    @Transactional
    public void followUser(Long userId, FollowerRequest followerRequest) {
        var user = userService.findById(userId);
        var follower = userService.findById(followerRequest.getFollowerId());

        if(!isFollower(user, follower)) {
            var followerEntity = new Follower();
            followerEntity.setFollower(follower);
            followerEntity.setUser(user);
            followerRepository.persist(followerEntity);
        }
    }

    @Override
    public boolean isFollower(User user, User follower) {
        return followerRepository.isFollower(user, follower);
    }

    @Override
    public FollowerPerUserResposnse findFollowers(Long userId) {
        User user = userService.findById(userId);
            List<Follower> followers = followerRepository.findByUser(user);
        var followersResponse = new ArrayList<FollowerResponse>();
        for(Follower f: followers) {
            FollowerResponse followerResponse = new FollowerResponse(f.getFollower().getId(), f.getFollower().getName());
            followersResponse.add(followerResponse);
        }
        return new FollowerPerUserResposnse(followers.size(), followersResponse);
    }

    @Override
    @Transactional
    public Boolean unfollow(Long userId, Long followerId) {
        User user, follower;
        try {
            user = userService.findById(userId);
            follower = userService.findById(followerId);
            followerRepository.deleteByUserAndFollower(user, follower);
        } catch(EntityNotFoundException e) {
            return FALSE;
        }
        return TRUE;
    }
}
