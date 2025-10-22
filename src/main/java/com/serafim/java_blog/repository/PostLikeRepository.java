package com.serafim.java_blog.repository;

import com.serafim.java_blog.domain.PostLike;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface PostLikeRepository extends MongoRepository<PostLike, String> {
    Optional<PostLike> findByUserIdAndPostId(String userId, String postId);
}
