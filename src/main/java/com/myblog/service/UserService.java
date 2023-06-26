package com.myblog.service;

import com.myblog.model.User;
import com.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public User createNewUser(User user) {
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public boolean deleteUser(String username) {
        userRepository.deleteByUsername(username);
        return true;
    }
}
