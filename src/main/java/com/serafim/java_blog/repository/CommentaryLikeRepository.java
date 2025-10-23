package com.serafim.java_blog.repository;

import com.serafim.java_blog.domain.CommentaryLike;
import com.serafim.java_blog.domain.PostLike;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommentaryLikeRepository extends MongoRepository<CommentaryLike, String> {
    Optional<CommentaryLike> findByUserIdAndCommentaryId(String userId, String commentaryId);
}
