package com.serafim.java_blog.controller;

import com.serafim.java_blog.controller.exception.PostAuthorization;
import com.serafim.java_blog.domain.Like;
import com.serafim.java_blog.domain.Post;
import com.serafim.java_blog.domain.User;
import com.serafim.java_blog.dto.PostRequestDTO;
import com.serafim.java_blog.dto.UpdatePostRequestDTO;
import com.serafim.java_blog.services.CommentaryService;
import com.serafim.java_blog.services.LikeService;
import com.serafim.java_blog.services.PostService;
import com.serafim.java_blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @PostMapping()
    public ResponseEntity<Post> insert(
            @Valid @ModelAttribute PostRequestDTO postRequestDTO,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        userService.findById(userId);
        return ResponseEntity.ok(postService.insert(postRequestDTO, userId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findById(@PathVariable String postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> like(
            @PathVariable String postId,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        userService.findById(userId);

        Post post = postService.findById(postId);
        Like like = likeService.findByUserIdAndPostId(userId, postId);

        if (like == null) {
            likeService.postLike(userId, postId);
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

    @GetMapping()
    public ResponseEntity<List<Post>> findAll(
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        userService.findById(userId);

        List<Post> posts = postService.findAllByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            @PathVariable() String postId,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        User user = userService.findById(userId);
        Post post = postService.findById(postId);

        if (!Objects.equals(user.getId(), post.getUserId())) {
            throw new PostAuthorization("You are not authorized to delete this post. Only the post owner may delete it.");
        }

        postService.delete(postId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> update(
            @PathVariable() String postId,
            @Valid @RequestBody UpdatePostRequestDTO updatePostRequestDTO,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        User user = userService.findById(userId);
        Post post = postService.findById(postId);

        if (!Objects.equals(user.getId(), post.getUserId())) {
            throw new PostAuthorization("You are not authorized to delete this post. Only the post owner may delete it.");
        }

        post.setTitle(Optional.ofNullable(updatePostRequestDTO.getTitle())
                .orElse(post.getTitle()));

        post.setText(Optional.ofNullable(updatePostRequestDTO.getText())
                .orElse(post.getText()));

        postService.update(post);

        return ResponseEntity.ok(post);
    }

}
