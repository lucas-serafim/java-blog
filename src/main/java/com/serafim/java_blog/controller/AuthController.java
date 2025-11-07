package com.serafim.java_blog.controller;

import com.serafim.java_blog.dto.AuthRequestDTO;
import com.serafim.java_blog.dto.AuthResponseDTO;
import com.serafim.java_blog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO responseDTO = authService.login(authRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
