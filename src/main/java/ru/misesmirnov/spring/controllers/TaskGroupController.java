package ru.misesmirnov.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.misesmirnov.spring.dto.TaskGroupDto;
import ru.misesmirnov.spring.dto.TaskGroupRequestDto;
import ru.misesmirnov.spring.exception.TaskGroupNotFoundException;
import ru.misesmirnov.spring.service.TaskGroupCrudService;

import java.util.Collection;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Группы задач", description = "API для управления группами задач пользователя")
@RequiredArgsConstructor
public class TaskGroupController {

    private final TaskGroupCrudService taskGroupCrudService;

    @Operation(summary = "Создать новую группу задач",
            description = "Создает новую группу задач для текущего пользователя")
    @PostMapping("/group/create")
    public ResponseEntity<TaskGroupDto> createTaskGroup(@RequestBody TaskGroupRequestDto taskGroupRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskGroupCrudService.createTaskGroup(taskGroupRequestDto));
    }

    @Operation(summary = "Обновить группу задач",
            description = "Обновляет существующую группу задач. Пользователь может обновлять только свои группы.")
    @PutMapping("/group/update")
    public ResponseEntity<TaskGroupDto> updateTaskGroup(@RequestBody TaskGroupDto taskGroupDto) {
        return ResponseEntity
                .ok(taskGroupCrudService.update(taskGroupDto.id(), taskGroupDto));
    }

    @Operation(summary = "Получить группу задач по ID",
            description = "Возвращает группу задач по ее идентификатору. Пользователь может получить только свои группы.")
    @GetMapping("/group/{id}")
    public ResponseEntity<TaskGroupDto> getTaskGroupById(@PathVariable Integer id) {
        return taskGroupCrudService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TaskGroupNotFoundException(
                        "По переданному id: " + id + " Группа задач не найдена"));
    }

    @Operation(summary = "Получить все группы задач пользователя",
            description = "Возвращает все группы задач текущего пользователя")
    @GetMapping("/groups")
    public Collection<TaskGroupDto> getAllTaskGroup() {
        return taskGroupCrudService.getUserTaskGroup();
    }

    @Operation(summary = "Удалить группу задач",
            description = "Удаляет группу задач по ее идентификатору. Пользователь может удалять только свои группы.")
    @DeleteMapping("/group/{id}")
    public ResponseEntity<TaskGroupDto> deleteTaskGroup(@PathVariable Integer id) {
        if (taskGroupCrudService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new TaskGroupNotFoundException("По переданному id: " + id + " Группа задач не найдена");
        }
    }
}
