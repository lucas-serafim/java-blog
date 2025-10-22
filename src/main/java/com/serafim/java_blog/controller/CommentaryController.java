package com.serafim.java_blog.controller;

import com.serafim.java_blog.domain.Commentary;
import com.serafim.java_blog.dto.CommentaryRequestDTO;
import com.serafim.java_blog.services.CommentaryService;
import com.serafim.java_blog.services.PostService;
import com.serafim.java_blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/commentaries")
public class CommentaryController {

    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/posts/{postId}/users/{userId}")
    public ResponseEntity<Commentary> insert(
            @PathVariable() String postId,
            @PathVariable() String userId,
            @RequestBody CommentaryRequestDTO commentaryRequestDTO
    ) {
        postService.findById(postId);
        userService.findById(userId);
        return ResponseEntity.ok(commentaryService.insert(commentaryRequestDTO, postId, userId));
    }
}
