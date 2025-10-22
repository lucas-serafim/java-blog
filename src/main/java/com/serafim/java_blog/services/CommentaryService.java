package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.Commentary;
import com.serafim.java_blog.dto.CommentaryRequestDTO;
import com.serafim.java_blog.repository.CommentaryRepository;
import com.serafim.java_blog.services.exception.CommentaryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentaryService {

    @Autowired
    private CommentaryRepository repository;

    public Commentary insert(CommentaryRequestDTO commentaryRequestDTO, String postId, String userId) {
        LocalDateTime now = LocalDateTime.now();

        Commentary commentary = new Commentary(
                UUID.randomUUID().toString(),
                postId,
                userId,
                commentaryRequestDTO.getText(),
                0,
                now,
                now
        );

        return repository.insert(commentary);
    }

    public Commentary findById(String id) {
        Optional<Commentary> user = repository.findById(id);
        return user.orElseThrow(() -> new CommentaryNotFoundException("Commentary not found. ID: " + id));
    }
}
