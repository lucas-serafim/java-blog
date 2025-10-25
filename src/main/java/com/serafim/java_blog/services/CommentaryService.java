package com.serafim.java_blog.services;

import com.serafim.java_blog.domain.Commentary;
import com.serafim.java_blog.dto.CommentaryRequestDTO;
import com.serafim.java_blog.repository.CommentaryRepository;
import com.serafim.java_blog.services.exception.CommentaryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommentaryService {

    @Autowired
    private CommentaryRepository repository;

    public Commentary comment(CommentaryRequestDTO commentaryRequestDTO, String postId, String userId) {
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

    public Commentary reply(CommentaryRequestDTO commentaryRequestDTO, String commentaryId, String postId, String userId) {
        LocalDateTime now = LocalDateTime.now();

        Commentary commentary = new Commentary(
                UUID.randomUUID().toString(),
                postId,
                userId,
                commentaryId,
                commentaryRequestDTO.getText(),
                0,
                now,
                now
        );

        return repository.insert(commentary);
    }

    public void update(Commentary commentary) {
        repository.save(commentary);
    }

    public void delete(String commentaryId) {
        repository.deleteById(commentaryId);
        repository.deleteAllByReplyToId(commentaryId);
    }

    public Commentary findById(String id) {
        Optional<Commentary> user = repository.findById(id);
        return user.orElseThrow(() -> new CommentaryNotFoundException("Commentary not found. ID: " + id));
    }

    public List<Commentary> findAllByPostId(String postId) {
        Optional<List<Commentary>> commentaries = repository.findAllByPostId(postId);
        return commentaries.map(this::structureCommentaries).orElse(null);
    }

    private List<Commentary> structureCommentaries(List<Commentary> flatCommentaries) {
        Map<String, Commentary> commentaryMap = new HashMap<>();

        for (Commentary commentary : flatCommentaries) {
            commentaryMap.put(commentary.getId(), commentary);
        }

        List<Commentary> primaryCommentaries = new ArrayList<>();

        for (Commentary commentary : flatCommentaries) {
            String replyToId = commentary.getReplyToId();

            if (replyToId != null) {
                Commentary principalComment = commentaryMap.get(replyToId);

                if (principalComment != null) {
                    principalComment.addReply(commentary);
                }
            } else {
                primaryCommentaries.add(commentary);
            }
        }

        for (Commentary primary : primaryCommentaries) {
            primary.getReplies().sort(Comparator.comparing(Commentary::getCreatedAt));
        }

        primaryCommentaries.sort(Comparator.comparing(Commentary::getCreatedAt));

        return primaryCommentaries;
    }
}
