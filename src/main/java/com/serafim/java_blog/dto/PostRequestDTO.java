package com.serafim.java_blog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50)
    private String title;

    @NotBlank(message = "Text is required")
    @Size(min = 1)
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
