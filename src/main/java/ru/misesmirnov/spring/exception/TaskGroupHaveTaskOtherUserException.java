package ru.misesmirnov.spring.exception;

public class TaskGroupHaveTaskOtherUserException extends RuntimeException {
    public TaskGroupHaveTaskOtherUserException(String message) {
        super(message);
    }
}
