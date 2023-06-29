package com.myblog.repository;

import com.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.stereotype.Component;

@Component
public class BlogTestBase {
    @Autowired
    DataGenerator dataGenerator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    protected TestRestTemplate restTemplate;
}
