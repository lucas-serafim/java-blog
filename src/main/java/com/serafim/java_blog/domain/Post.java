package com.serafim.java_blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document("posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private String id;
    private String userId;

    private String title;
    private String text;
    private Integer likes;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post(String id, String userId, String title, String text, Integer likes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.likes = likes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.images = new ArrayList<>();
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
