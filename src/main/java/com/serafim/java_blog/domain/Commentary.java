package com.serafim.java_blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("commentaries")
@Getter
@Setter
@NoArgsConstructor
public class Commentary {

    @Id
    private String id;
    private String postId;
    private String userId;

    private String replyToId;

    private String text;
    private Integer likes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Commentary(String id, String postId, String userId, String text, Integer likes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.likes = likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Commentary(String id, String postId, String userId, String replyToId, String text, Integer likes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.replyToId = replyToId;
        this.text = text;
        this.likes = likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void increaseLike() {
        this.likes++;
    }

    public void decreaseLike() {
        if (this.likes > 0) {
            this.likes--;
        }
    }
}
