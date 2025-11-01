package com.serafim.java_blog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile[] images = new MultipartFile[0];

    public PostRequestDTO(String title, String text) {
        this.title = title;
        this.text = text;
        this.images = new MultipartFile[0];
    }

    public PostRequestDTO(String title, String text, MultipartFile[] images) {
        this.title = title;
        this.text = text;
        this.images = images != null ? images : new MultipartFile[0];
    }
}
