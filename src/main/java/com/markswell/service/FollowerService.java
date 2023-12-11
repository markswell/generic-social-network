package com.markswell.service;

import com.markswell.domain.User;
import com.markswell.dto.FollowerPerUserResposnse;
import com.markswell.dto.FollowerRequest;
import jakarta.transaction.Transactional;

public interface FollowerService {
    @Transactional
    void followUser(Long id, FollowerRequest followerRequest);

    boolean isFollower(User user, User follower);

    FollowerPerUserResposnse findFollowers(Long userId);

    @Transactional
    Boolean unfollow(Long userId, Long followerId);
}
