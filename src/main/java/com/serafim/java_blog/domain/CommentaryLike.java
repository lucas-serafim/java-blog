package com.serafim.java_blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@NoArgsConstructor
public class CommentaryLike extends Like {

    private String commentaryId;

    public CommentaryLike(String id, String userId, String commentaryId, LocalDateTime likedAt) {
        super(id, userId, likedAt);
        this.commentaryId = commentaryId;
    }
}
