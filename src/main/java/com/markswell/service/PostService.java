package com.markswell.service;

import com.markswell.domain.Post;
import com.markswell.dto.PostRequest;
import com.markswell.dto.PostResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PostService {
    @Transactional
    Post savePost(Long id, PostRequest postRequest);

    List<PostResponse> getPosts(Long userId, Long followerId);
}
