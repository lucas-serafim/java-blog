package com.serafim.java_blog.controller;

import com.serafim.java_blog.domain.User;
import com.serafim.java_blog.dto.UserRequestDTO;
import com.serafim.java_blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping()
    public ResponseEntity<User> insert(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(service.insert(userRequestDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable String userId) {
        return ResponseEntity.ok(service.findById(userId));
    }
}
