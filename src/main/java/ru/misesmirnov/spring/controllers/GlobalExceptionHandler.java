package ru.misesmirnov.spring.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.misesmirnov.spring.dto.ErrorDto;
import ru.misesmirnov.spring.exception.TaskGroupHaveTaskOtherUserException;
import ru.misesmirnov.spring.exception.TaskGroupNotFoundException;
import ru.misesmirnov.spring.exception.TaskNotFoundException;
import ru.misesmirnov.spring.exception.UserAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorDto.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(TaskGroupNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTaskGroupNotFound(TaskGroupNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(TaskGroupHaveTaskOtherUserException.class)
    public ResponseEntity<ErrorDto> TaskGroupHaveTaskOtherUser(TaskGroupHaveTaskOtherUserException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorDto.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                .body(ErrorDto.builder()
                        .message("Внутренняя ошибка сервера")
                        .build());
    }
}
