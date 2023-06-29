package com.myblog.repository;

import com.myblog.model.File;
import com.myblog.model.Post;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class BlogJpaAppTests extends BlogTestBase {
    private boolean generatedData = true;

    @PostConstruct
    public void init() {
        log.info("Check if initialized");
        if (dataGenerator.getInitialized().get() == false && generatedData) {
            dataGenerator.generateSampleData();
        }
    }

    @Test
    @Transactional
    @Commit
    public void testOneToManyDeletion() {
        log.info("Delete user: {}", "User 1");
        userService.deleteUser("user1");
        log.info("Print all users");
        userRepository.findAll().stream().forEach(
                u -> log.info("User: {}, {}", u.getId(), u.getUsername())
        );
        long count = userRepository.findAll().stream().filter(
                u -> "user1".equals(u.getUsername())
        ).count();
        Assert.assertEquals(0l, count);
    }

    @Test
    @Transactional
    @Commit
    public void testManyToManyWithPostAndFiles() {
        Post post = postRepository.findById(1L).get();

        File file = new File();
        file.setName("main_image");
        fileRepository.save(file);

        File file1 = new File();
        file1.setName("second_image");
        fileRepository.save(file1);
//        fileRepository.refresh(file);
//        fileRepository.refresh(file1);

        Set<File> files = post.getFiles();
        if (files == null) {
            files = new HashSet<>();
        }
        files.add(file);
        files.add(file1);

        post.setFiles(files);
        postRepository.save(post);

        List<File> allFiles = fileRepository.findAll();
        allFiles.forEach(f -> {
            Set<Post> posts = f.getPosts();
            posts.forEach(p -> {
                log.info("Post: {} attached with file: {}", p.getTitle(), f.getName());
            });
        });

        post.getFiles().forEach(f -> {
            log.info("File: {} attached to Post: {}", f.getName(), post.getTitle());
        });
    }
}
