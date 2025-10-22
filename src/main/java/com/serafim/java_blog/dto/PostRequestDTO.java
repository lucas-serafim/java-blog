package com.serafim.java_blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDTO {
    private String title;
    private String text;
    private String[] images;

    public PostRequestDTO(String title, String text) {
        this.title = title;
        this.text = text;
        this.images = null;
    }

    public PostRequestDTO(String title, String text, String[] images) {
        this.title = title;
        this.text = text;
        this.images = images;
    }
}
