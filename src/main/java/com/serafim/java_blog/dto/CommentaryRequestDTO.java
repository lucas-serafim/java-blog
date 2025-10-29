package com.serafim.java_blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentaryRequestDTO {

    @NotBlank(message = "Text is required")
    @Size(min = 1)
    private String text;
}
