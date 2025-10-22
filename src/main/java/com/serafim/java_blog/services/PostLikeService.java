package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.PostLike;
import com.serafim.java_blog.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeRepository repository;

    public PostLike insert(String userId, String postId) {
        PostLike postLike = new PostLike(
                UUID.randomUUID().toString(),
                userId,
                postId
        );

        return repository.insert(postLike);
    }

    public void delete(PostLike postLike) {
        repository.delete(postLike);
    }

    public PostLike findByUserIdAndPostId(String userId, String postId) {
        Optional<PostLike> postLike = repository.findByUserIdAndPostId(userId, postId);
        return postLike.orElse(null);
    }
}
