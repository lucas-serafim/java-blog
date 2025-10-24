package com.serafim.java_blog.domain.enums;

import lombok.Getter;

@Getter
public enum LikeType {

    POST("post"),
    COMMENTARY("commentary");

    private final String type;

    LikeType(String type) {
        this.type = type;
    }
}
