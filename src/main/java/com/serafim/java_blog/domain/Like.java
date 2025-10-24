package com.serafim.java_blog.domain;

import com.serafim.java_blog.domain.enums.LikeType;
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
    private String entityId;
    private LikeType entityType;
    private LocalDateTime likedAt;
}
