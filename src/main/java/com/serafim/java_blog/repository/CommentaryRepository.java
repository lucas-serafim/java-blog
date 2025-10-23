package com.serafim.java_blog.repository;

import com.serafim.java_blog.domain.Commentary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentaryRepository extends MongoRepository<Commentary, String> {
    Optional<List<Commentary>> findAllByPostId(String postId);
}
