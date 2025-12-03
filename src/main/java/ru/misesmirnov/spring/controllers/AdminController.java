package ru.misesmirnov.spring.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.misesmirnov.spring.dto.*;
import ru.misesmirnov.spring.exception.TaskGroupNotFoundException;
import ru.misesmirnov.spring.exception.TaskNotFoundException;
import ru.misesmirnov.spring.service.StatisticService;
import ru.misesmirnov.spring.service.TaskCrudService;
import ru.misesmirnov.spring.service.TaskGroupCrudService;
import ru.misesmirnov.spring.service.UserCrudService;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserCrudService userCrudService;
    private final TaskCrudService taskCrudService;
    private final TaskGroupCrudService taskGroupCrudService;
    private final StatisticService statisticService;

    @GetMapping("/users")
    public Collection<UserDto> getAllUsers() {
        return userCrudService.getList();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
        return userCrudService.getById(userId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(
                        "По переданному id: " + userId + " Пользователь не найден"));
    }

    @GetMapping("/users/{userId}/tasks")
    public Collection<TaskDto> getUserTasks(@PathVariable Integer userId) {
        return taskCrudService.getUserTasks(userId);
    }

    @GetMapping("/tasks")
    public Collection<TaskDto> getAllTasks() {
        return taskCrudService.getList();
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Integer taskId) {
        return taskCrudService.getById(taskId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TaskNotFoundException(
                        "По переданному id: " + taskId + " Задача не найдена"));
    }

    @GetMapping("/task-groups")
    public Collection<TaskGroupDto> getAllTaskGroups() {
        return taskGroupCrudService.getList();
    }

    @GetMapping("/task-groups/{groupId}")
    public ResponseEntity<TaskGroupDto> getTasksByGroup(@PathVariable Integer groupId) {
        return taskGroupCrudService.getById(groupId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TaskGroupNotFoundException(
                        "По переданному id: " + groupId + " Группа задач не найдена"));
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsDto> getStatistics() {
        return ResponseEntity.ok(statisticService.getStatistics());
    }

    @GetMapping("/statistics/status")
    public Collection<StatusStatisticDto> getTasksByStatus() {
        return statisticService.getTasksByStatus();
    }
}
