package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.Post;
import com.serafim.java_blog.dto.PostRequestDTO;
import com.serafim.java_blog.repository.PostRepository;
import com.serafim.java_blog.services.exception.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post insert(PostRequestDTO postRequestDTO, String userId) {
        LocalDateTime now = LocalDateTime.now();

        Post post = new Post(
                UUID.randomUUID().toString(),
                userId,
                postRequestDTO.getTitle(),
                postRequestDTO.getText(),
                0,
                postRequestDTO.getImages(),
                now,
                now
        );

        return repository.insert(post);
    }

    public void update(Post post) {
        repository.save(post);
    }

    public Post findById(String id) {
        Optional<Post> post = repository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException("Post not found. ID: " + id));
    }

    public List<Post> findAllByUserId(String userId) {
        Optional<List<Post>> posts = repository.findAllByUserId(userId);
        return posts.orElse(null);
    }
}
