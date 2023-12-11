package com.markswell.service.impl;

import jakarta.inject.Inject;
import com.markswell.domain.User;
import com.markswell.dto.UserRequest;
import com.markswell.service.UserService;
import jakarta.transaction.Transactional;
import com.markswell.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import static java.util.Objects.isNull;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    public User findById(Long userId) {
        var user = userRepository.findById(userId);
        if(isNull(user)){
            throw new EntityNotFoundException("It was not found a user with %d id".formatted(userId));
        }
        return user;
    }

    @Override
    public List<User> listAll() {
        return userRepository.listAll();
    }

    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {
        var user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());
        userRepository.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User update(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId);
        if(!isNull(user)){
            user.setAge(userRequest.getAge());
            user.setName(userRequest.getName());
            return user;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(Long userId) {
        return userRepository.deleteById(userId);
    }
}
