package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.User;
import com.serafim.java_blog.dto.UserRequestDTO;
import com.serafim.java_blog.repository.UserRepository;
import com.serafim.java_blog.services.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User insert(UserRequestDTO userRequestDTO) {
        /*
            TODO
                1. Verify if user already exists by email
                2. Encrypt user password and save it
        */

        LocalDateTime now = LocalDateTime.now();

        User user = new User(
                UUID.randomUUID().toString(),
                userRequestDTO.getName(),
                userRequestDTO.getEmail(),
                userRequestDTO.getPassword(),
                now,
                now
        );

        return repository.insert(user);
    }

    public User findById(String id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User not found. ID: " + id));
    }
}
