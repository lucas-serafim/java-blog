package com.serafim.java_blog.controller;

import com.serafim.java_blog.domain.Like;
import com.serafim.java_blog.domain.Post;
import com.serafim.java_blog.domain.enums.LikeType;
import com.serafim.java_blog.dto.PostRequestDTO;
import com.serafim.java_blog.services.CommentaryService;
import com.serafim.java_blog.services.LikeService;
import com.serafim.java_blog.services.PostService;
import com.serafim.java_blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentaryService commentaryService;

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
        Like like = likeService.findByUserIdAndEntityId(userId, postId);

        if (like == null) {
            likeService.insert(userId, postId, LikeType.POST);
            post.increaseLike();
        } else {
            likeService.delete(like);
            post.decreaseLike();
        }

        postService.update(post);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Post>> findAllByUserId(
            @PathVariable() String userId
    ) {
        userService.findById(userId);

        List<Post> posts = postService.findAllByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            @PathVariable() String postId
    ) {
        commentaryService.deleteAllByPostId(postId);
        postService.delete(postId);

        return ResponseEntity.noContent().build();
    }
}
