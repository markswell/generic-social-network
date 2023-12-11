package com.markswell.service;

import com.markswell.domain.User;
import com.markswell.dto.UserRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    User findById(Long userId);

    List<User> listAll();

    @Transactional
    User createUser(UserRequest userRequest);

    @Transactional
    User update(Long userId, UserRequest userRequest);

    @Transactional
    boolean delete(Long userId);
}
