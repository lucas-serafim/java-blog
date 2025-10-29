package com.serafim.java_blog.controller.exception;

public class CommentaryAuthorization extends RuntimeException {

    public CommentaryAuthorization(String message) {
        super(message);
    }
}
