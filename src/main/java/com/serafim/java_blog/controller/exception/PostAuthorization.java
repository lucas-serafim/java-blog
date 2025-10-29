package com.serafim.java_blog.controller.exception;

public class PostAuthorization extends RuntimeException {

    public PostAuthorization(String message) {
        super(message);
    }
}
