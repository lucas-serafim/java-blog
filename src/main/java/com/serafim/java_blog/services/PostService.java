package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.Image;
import com.serafim.java_blog.domain.Post;
import com.serafim.java_blog.dto.PostRequestDTO;
import com.serafim.java_blog.repository.CommentaryRepository;
import com.serafim.java_blog.repository.LikeRepository;
import com.serafim.java_blog.repository.PostRepository;
import com.serafim.java_blog.services.exception.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentaryRepository commentaryRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private S3Service s3Service;

    public Post insert(PostRequestDTO postRequestDTO, String userId) {
        LocalDateTime now = LocalDateTime.now();

        List<Image> images = new ArrayList<>();

        for (MultipartFile file : postRequestDTO.getImages()) {
            String keyName = s3Service.putObject(file);;
            String url = s3Service.getUrl(keyName);

            Image image = new Image(
                    url,
                    keyName
            );

            images.add(image);
        }

        Post post = new Post(
                UUID.randomUUID().toString(),
                userId,
                postRequestDTO.getTitle(),
                postRequestDTO.getText(),
                0,
                images,
                now,
                now
        );

        return postRepository.insert(post);
    }

    public void update(Post post) {
        postRepository.save(post);
    }

    public Post findById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new PostNotFoundException("Post not found. ID: " + id));
    }

    public List<Post> findAllByUserId(String userId) {
        Optional<List<Post>> posts = postRepository.findAllByUserId(userId);
        return posts.orElse(null);
    }

    @Transactional
    public void delete(Post post) {
        String postId = post.getId();

        likeRepository.deleteAllByPostId(postId);

        commentaryRepository.deleteAllByPostId(postId);

        post.getImages().forEach(image -> deleteImage(image.getKeyName()));

        postRepository.deleteById(postId);
    }

    @Transactional
    public void deleteImage(String keyName) {
        s3Service.deleteObject(keyName);
    }
}
