package com.serafim.java_blog.repository;

import com.serafim.java_blog.domain.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LikeRepository extends MongoRepository<Like, String> {

    Optional<Like> findByUserIdAndPostId(String userId, String postId);

    Optional<Like> findByUserIdAndCommentaryId(String userId, String postId);

    void deleteAllByPostId(String postId);

    void deleteAllByCommentaryId(String commentaryId);
}
