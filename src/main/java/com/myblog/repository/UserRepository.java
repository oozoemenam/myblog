package com.myblog.repository;

import com.myblog.model.User;
import com.myblog.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

//    @Query("SELECT u FROM User u")
//    Stream<User> streamAll();
}
