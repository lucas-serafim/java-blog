package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.Like;
import com.serafim.java_blog.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    @Autowired
    private LikeRepository repository;

    public void postLike(String userId, String postId) {
        LocalDateTime now = LocalDateTime.now();

        Like like = new Like(
                UUID.randomUUID().toString(),
                userId,
                postId,
                now
        );

        repository.insert(like);
    }

    public void commentaryLike(String userId, String postId, String commentaryId) {
        LocalDateTime now = LocalDateTime.now();

        Like like = new Like(
                UUID.randomUUID().toString(),
                userId,
                postId,
                commentaryId,
                now
        );

        repository.insert(like);
    }

    @Transactional
    public void delete(Like like) {
        repository.delete(like);
    }

    public Like findByUserIdAndPostId(String userId, String postId) {
        return repository.findByUserIdAndPostId(userId, postId).orElse(null);
    }

    public Like findByUserIdAndCommentaryId(String userId, String postId) {
        return repository.findByUserIdAndCommentaryId(userId, postId).orElse(null);
    }
}
