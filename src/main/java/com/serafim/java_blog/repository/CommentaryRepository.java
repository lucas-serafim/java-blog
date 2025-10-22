package com.serafim.java_blog.repository;

import com.serafim.java_blog.domain.Commentary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CommentaryRepository extends MongoRepository<Commentary, String> {
}
