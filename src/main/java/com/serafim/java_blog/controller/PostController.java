package com.serafim.java_blog.controller;

import com.serafim.java_blog.domain.Post;
import com.serafim.java_blog.domain.PostLike;
import com.serafim.java_blog.dto.PostRequestDTO;
import com.serafim.java_blog.services.PostLikeService;
import com.serafim.java_blog.services.PostService;
import com.serafim.java_blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostLikeService postLikeService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<Post> insert(
            @PathVariable() String userId,
            @RequestBody PostRequestDTO postRequestDTO
    ) {
        userService.findById(userId);
        return ResponseEntity.ok(postService.insert(postRequestDTO, userId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findById(@PathVariable String postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    @PostMapping("/{postId}/users/{userId}/like")
    public ResponseEntity<Void> like(
            @PathVariable() String userId,
            @PathVariable String postId
    ) {
        userService.findById(userId);

        Post post = postService.findById(postId);
        PostLike postLike = postLikeService.findByUserIdAndPostId(userId, postId);

        if (postLike == null) {
            postLikeService.insert(userId, postId);
            post.increaseLike();
        } else {
            postLikeService.delete(postLike);
            post.decreaseLike();
        }

        postService.update(post);

        return ResponseEntity.noContent().build();
    }
}
