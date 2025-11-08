package com.serafim.java_blog.controller;

import com.serafim.java_blog.controller.exception.CommentaryAuthorization;
import com.serafim.java_blog.domain.Commentary;
import com.serafim.java_blog.domain.Like;
import com.serafim.java_blog.domain.Post;
import com.serafim.java_blog.domain.User;
import com.serafim.java_blog.dto.CommentaryRequestDTO;
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

@RestController
@RequestMapping(value = "/commentaries")
public class CommentaryController {

    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<Commentary> comment(
            @PathVariable() String postId,
            @Valid @RequestBody CommentaryRequestDTO commentaryRequestDTO,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        userService.findById(userId);
        postService.findById(postId);
        return ResponseEntity.ok(commentaryService.comment(commentaryRequestDTO, postId, userId));
    }

    @PostMapping("/{commentaryId}/posts/{postId}/reply-comment")
    public ResponseEntity<Commentary> reply(
            @PathVariable() String commentaryId,
            @PathVariable() String postId,
            @Valid @RequestBody CommentaryRequestDTO commentaryRequestDTO,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        userService.findById(userId);
        postService.findById(postId);
        commentaryService.findById(commentaryId);

        return ResponseEntity.ok(commentaryService.reply(commentaryRequestDTO, commentaryId, postId, userId));
    }

    @PostMapping("/{commentaryId}/posts/{postId}/like")
    public ResponseEntity<Void> like(
            @PathVariable() String postId,
            @PathVariable() String commentaryId,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        userService.findById(userId);
        postService.findById(postId);

        Commentary commentary = commentaryService.findById(commentaryId);
        Like like = likeService.findByUserIdAndCommentaryId(userId, commentaryId);

        if (like == null) {
            likeService.commentaryLike(userId, postId, commentaryId);
            commentary.increaseLike();
        } else {
            likeService.delete(like);
            commentary.decreaseLike();
        }

        commentaryService.update(commentary);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<Commentary>> findAllByPostId(
            @PathVariable() String postId
    ) {
        List<Commentary> commentaries = commentaryService.findAllByPostId(postId);
        return ResponseEntity.ok(commentaries);
    }

    @DeleteMapping("/{commentaryId}/posts/{postId}")
    public ResponseEntity<Void> delete(
            @PathVariable() String commentaryId,
            @PathVariable() String postId,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        User user = userService.findById(userId);
        Post post = postService.findById(postId);
        Commentary commentary = commentaryService.findById(commentaryId);

        if (!Objects.equals(commentary.getUserId(), user.getId()) || !Objects.equals(post.getUserId(), user.getId())) {
            throw new CommentaryAuthorization("You are not authorized to delete this comment. Only the comment owner or post owner may delete it.");
        }

        commentaryService.delete(commentaryId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{commentaryId}")
    public ResponseEntity<Commentary> update(
            @PathVariable() String commentaryId,
            @Valid @RequestBody CommentaryRequestDTO commentaryRequestDTO,
            JwtAuthenticationToken token
    ) {
        String userId = token.getName();

        User user = userService.findById(userId);
        Commentary commentary = commentaryService.findById(commentaryId);

        if (!Objects.equals(user.getId(), commentary.getUserId())) {
            throw new CommentaryAuthorization("You are not authorized to delete this comment. Only the comment owner may delete it.");
        }

        commentary.setText(commentaryRequestDTO.getText());
        commentary.setIsEdited(true);

        commentaryService.update(commentary);

        return ResponseEntity.ok(commentary);
    }
}
