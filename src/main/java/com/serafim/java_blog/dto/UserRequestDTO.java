package com.serafim.java_blog.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "It should be a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 25)
    private String password;
}
