package com.serafim.java_blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public abstract class Like {

    @Id
    private String id;
    private String userId;
    private LocalDateTime likedAt;

    public Like(String id, String userId, LocalDateTime likedAt) {
        this.id = id;
        this.userId = userId;
        this.likedAt = likedAt;
    }
}
