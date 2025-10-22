package com.serafim.java_blog.services.exception;

public class CommentaryNotFoundException extends RuntimeException {

    public CommentaryNotFoundException(String message) {
        super(message);
    }
}
