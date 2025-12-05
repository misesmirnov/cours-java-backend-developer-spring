package ru.misesmirnov.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Администрирование", description = "API для администраторов системы")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserCrudService userCrudService;
    private final TaskCrudService taskCrudService;
    private final TaskGroupCrudService taskGroupCrudService;
    private final StatisticService statisticService;

    @Operation(summary = "Получить всех пользователей",
            description = "Возвращает список всех зарегистрированных пользователей. Требует роль ADMIN.")
    @GetMapping("/users")
    public Collection<UserDto> getAllUsers() {
        return userCrudService.getList();
    }

    @Operation(summary = "Получить пользователя по ID",
            description = "Возвращает информацию о конкретном пользователе по его идентификатору")
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
        return userCrudService.getById(userId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(
                        "По переданному id: " + userId + " Пользователь не найден"));
    }

    @Operation(summary = "Получить задачи пользователя",
            description = "Возвращает все задачи, принадлежащие указанному пользователю")
    @GetMapping("/users/{userId}/tasks")
    public Collection<TaskDto> getUserTasks(@PathVariable Integer userId) {
        return taskCrudService.getUserTasks(userId);
    }

    @Operation(summary = "Получить все задачи",
            description = "Возвращает все задачи в системе независимо от пользователя")
    @GetMapping("/tasks")
    public Collection<TaskDto> getAllTasks() {
        return taskCrudService.getList();
    }

    @Operation(summary = "Получить задачу по ID",
            description = "Возвращает информацию о конкретной задаче по ее идентификатору")
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Integer taskId) {
        return taskCrudService.getByIdForAdmin(taskId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TaskNotFoundException(
                        "По переданному id: " + taskId + " Задача не найдена"));
    }

    @Operation(summary = "Получить все группы задач",
            description = "Возвращает все группы задач в системе")
    @GetMapping("/task-groups")
    public Collection<TaskGroupDto> getAllTaskGroups() {
        return taskGroupCrudService.getList();
    }

    @Operation(summary = "Получить группу задач по ID",
            description = "Возвращает информацию о конкретной группе задач по ее идентификатору")
    @GetMapping("/task-groups/{groupId}")
    public ResponseEntity<TaskGroupDto> getTasksByGroup(@PathVariable Integer groupId) {
        return taskGroupCrudService.getByIdForAdmin(groupId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TaskGroupNotFoundException(
                        "По переданному id: " + groupId + " Группа задач не найдена"));
    }

    @Operation(summary = "Получить общую статистику",
            description = "Возвращает статистику по задачам и группам")
    @GetMapping("/statistics")
    public ResponseEntity<StatisticsDto> getStatistics() {
        return ResponseEntity.ok(statisticService.getStatistics());
    }

    @Operation(summary = "Получить статистику по статусам задач",
            description = "Возвращает количество задач по каждому статусу")
    @GetMapping("/statistics/status")
    public Collection<StatusStatisticDto> getTasksByStatus() {
        return statisticService.getTasksByStatus();
    }
}
