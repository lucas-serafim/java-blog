package com.serafim.java_blog.repository;

import com.serafim.java_blog.domain.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LikeRepository extends MongoRepository<Like, String> {

    Optional<Like> findByUserIdAndEntityId(String userId, String entityId);
}
