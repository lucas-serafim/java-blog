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
@AllArgsConstructor
@NoArgsConstructor
public class Commentary {

    @Id
    private String id;
    private String postId;
    private String userId;

    private String text;
    private Integer likes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
