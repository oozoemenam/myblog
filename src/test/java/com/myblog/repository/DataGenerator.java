package com.myblog.repository;

import com.myblog.enums.PostStatus;
import com.myblog.model.Blog;
import com.myblog.model.Post;
import com.myblog.model.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Data
@Slf4j
public class DataGenerator {
    private AtomicBoolean initialized = new AtomicBoolean(false);

    List<String> blogNames = Arrays.asList("PartyBlog", "ScienceBlog");

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Transactional
    public void generateSampleData() {
        this.initialized.compareAndSet(false, true);
        log.info("Generating sample data");
        List<Blog> blogs = new ArrayList<>();

        User user1 = new User("User 1", "password", "user1@domain.com");
        User user2 = new User("User 2", "password", "user2@domain.com");
        User user3 = new User("User 3", "password", "user3@domain.com");

        userRepository.save(user1);
        userRepository.refresh(user1);
        userRepository.save(user2);
        userRepository.refresh(user2);
        userRepository.save(user3);
        userRepository.refresh(user3);

        blogNames.forEach(name -> {
            Blog blog = Blog.builder()
                    .name(name)
                    .about("Sample blog")
                    .publishedAt(LocalDateTime.now())
                    .build();

            blogRepository.save(blog);
            blogRepository.refresh(blog);
            blogs.add(blog);

            Post post1 = Post.builder()
                    .title("Lorem Ipsum 1")
                    .content("Sample content 1")
                    .user(user1)
                    .blog(blog)
                    .postStatus(PostStatus.ACTIVE)
                    .build();
            Post post2 = Post.builder()
                    .title("Lorem Ipsum 2")
                    .content("Sample content 2")
                    .user(user2)
                    .blog(blog)
                    .postStatus(PostStatus.ACTIVE)
                    .build();
            Post post3 = Post.builder()
                    .title("Lorem Ipsum 3")
                    .content("Sample content 3")
                    .user(user3)
                    .blog(blog)
                    .postStatus(PostStatus.ACTIVE)
                    .build();
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);
        });
        log.info("Generation complete");
    }
}
