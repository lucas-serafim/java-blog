package com.serafim.java_blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "It should be a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 25)
    private String password;
}
