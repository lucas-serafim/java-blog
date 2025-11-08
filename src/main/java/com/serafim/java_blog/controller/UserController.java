package com.serafim.java_blog.controller;

import com.serafim.java_blog.domain.User;
import com.serafim.java_blog.dto.AuthRequestDTO;
import com.serafim.java_blog.dto.AuthResponseDTO;
import com.serafim.java_blog.dto.UserRequestDTO;
import com.serafim.java_blog.services.AuthService;
import com.serafim.java_blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.signUp(userRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO responseDTO = authService.login(authRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
}
