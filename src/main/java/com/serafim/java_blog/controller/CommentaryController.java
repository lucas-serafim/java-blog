package com.serafim.java_blog.controller;

import com.serafim.java_blog.domain.Commentary;
import com.serafim.java_blog.domain.Like;
import com.serafim.java_blog.domain.enums.LikeType;
import com.serafim.java_blog.dto.CommentaryRequestDTO;
import com.serafim.java_blog.services.CommentaryService;
import com.serafim.java_blog.services.LikeService;
import com.serafim.java_blog.services.PostService;
import com.serafim.java_blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/posts/{postId}/users/{userId}")
    public ResponseEntity<Commentary> comment(
            @PathVariable() String postId,
            @PathVariable() String userId,
            @RequestBody CommentaryRequestDTO commentaryRequestDTO
    ) {
        postService.findById(postId);
        userService.findById(userId);
        return ResponseEntity.ok(commentaryService.comment(commentaryRequestDTO, postId, userId));
    }

    @PostMapping("/{commentaryId}/posts/{postId}/users/{userId}/reply-comment")
    public ResponseEntity<Commentary> reply(
            @PathVariable() String commentaryId,
            @PathVariable() String postId,
            @PathVariable() String userId,
            @RequestBody CommentaryRequestDTO commentaryRequestDTO
    ) {
        userService.findById(userId);
        postService.findById(postId);
        commentaryService.findById(commentaryId);

        return ResponseEntity.ok(commentaryService.reply(commentaryRequestDTO, commentaryId, postId, userId));
    }

    @PostMapping("/{commentaryId}/posts/{postId}/users/{userId}/like")
    public ResponseEntity<Void> like(
            @PathVariable() String postId,
            @PathVariable() String commentaryId,
            @PathVariable() String userId
    ) {
        userService.findById(userId);
        postService.findById(postId);

        Commentary commentary = commentaryService.findById(commentaryId);
        Like like = likeService.findByUserIdAndEntityId(userId, commentaryId);

        if (like == null) {
            likeService.insert(userId, commentaryId, LikeType.COMMENTARY);
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

    @DeleteMapping("/{commentaryId}")
    public ResponseEntity<Void> delete(
            @PathVariable() String commentaryId
    ) {
        commentaryService.delete(commentaryId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{commentaryId}")
    public ResponseEntity<Commentary> update(
            @PathVariable() String commentaryId,
            @RequestBody CommentaryRequestDTO commentaryRequestDTO
    ) {
        Commentary commentary = commentaryService.findById(commentaryId);

        commentary.setText(commentaryRequestDTO.getText());
        commentary.setIsEdited(true);

        commentaryService.update(commentary);

        return ResponseEntity.ok(commentary);
    }
}
