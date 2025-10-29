package com.serafim.java_blog.controller;

import com.serafim.java_blog.controller.exception.CommentaryAuthorization;
import com.serafim.java_blog.controller.exception.PostAuthorization;
import com.serafim.java_blog.domain.CustomError;
import com.serafim.java_blog.services.exception.CommentaryNotFoundException;
import com.serafim.java_blog.services.exception.PostNotFoundException;
import com.serafim.java_blog.services.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            PostNotFoundException.class,
            UserNotFoundException.class,
            CommentaryNotFoundException.class
    })
    public ResponseEntity<CustomError> notFoundException(RuntimeException exception) {
        CustomError customError = CustomError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .errors(List.of(exception.getMessage()))
                .build();
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CommentaryAuthorization.class,
            PostAuthorization.class
    })
    public ResponseEntity<CustomError> commentaryAuthorization(RuntimeException exception) {
        CustomError customError = CustomError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.name())
                .errors(List.of(exception.getMessage()))
                .build();
        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> argumentNotValidException(MethodArgumentNotValidException exception) {

        List<String> errorList = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        CustomError customError = CustomError
                .builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errorList)
                .build();
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

}
