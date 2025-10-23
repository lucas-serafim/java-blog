package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.CommentaryLike;
import com.serafim.java_blog.repository.CommentaryLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentaryLikeService {

    @Autowired
    private CommentaryLikeRepository repository;

    public CommentaryLike insert(String userId, String commentaryId) {

        LocalDateTime now = LocalDateTime.now();

        CommentaryLike commentaryLike = new CommentaryLike(
                UUID.randomUUID().toString(),
                userId,
                commentaryId,
                now
        );

        return repository.insert(commentaryLike);
    }

    public void delete(CommentaryLike commentaryLike) {
        repository.delete(commentaryLike);
    }

    public CommentaryLike findByUserIdAndCommentaryId(String userId, String commentaryId) {
        Optional<CommentaryLike> commentaryLike = repository.findByUserIdAndCommentaryId(userId, commentaryId);
        return commentaryLike.orElse(null);
    }
}
