package com.serafim.java_blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document
@Getter
@Setter
@NoArgsConstructor
public class PostLike extends Like {
    private String postId;

    public PostLike(String id, String userId, String postId, LocalDateTime likedAt) {
        super(id, userId, likedAt);
        this.postId = postId;
    }
}
