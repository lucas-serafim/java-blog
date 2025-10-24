package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.Like;
import com.serafim.java_blog.domain.enums.LikeType;
import com.serafim.java_blog.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService {

    @Autowired
    private LikeRepository repository;

    public Like insert(String userId, String entityId, LikeType likeType) {
        LocalDateTime now = LocalDateTime.now();

        Like like = new Like(
                UUID.randomUUID().toString(),
                userId,
                entityId,
                likeType,
                now
        );

        return repository.insert(like);
    }

    public void delete(Like like) {
        repository.delete(like);
    }

    public Like findByUserIdAndEntityId(String userId, String postId) {
        Optional<Like> like = repository.findByUserIdAndEntityId(userId, postId);
        return like.orElse(null);
    }
}
