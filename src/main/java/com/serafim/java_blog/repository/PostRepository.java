package com.serafim.java_blog.repository;

import com.serafim.java_blog.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends MongoRepository<Post, String> {

    Optional<List<Post>> findAllByUserId(String userId);
}
