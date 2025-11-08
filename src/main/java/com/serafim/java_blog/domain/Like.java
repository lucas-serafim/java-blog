package com.serafim.java_blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    private String id;
    private String userId;
    private String postId;
    private String commentaryId;
    private LocalDateTime likedAt;

    public Like(String id, String userId, String postId, LocalDateTime likedAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.likedAt = likedAt;
    }
}
