package com.serafim.java_blog.repository;

import com.serafim.java_blog.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PostRepository extends MongoRepository<Post, String> {
}
