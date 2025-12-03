package ru.misesmirnov.spring.exception;

public class TaskGroupNotFoundException extends RuntimeException {
    public TaskGroupNotFoundException(String message) {
        super(message);
    }
}
