package ru.misesmirnov.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.misesmirnov.spring.dto.TaskDto;
import ru.misesmirnov.spring.dto.TaskRequestDto;
import ru.misesmirnov.spring.exception.TaskNotFoundException;
import ru.misesmirnov.spring.service.TaskCrudService;

import java.util.Collection;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Задачи", description = "API для управления задачами пользователя")
@RequiredArgsConstructor
public class TaskController {

    private final TaskCrudService taskCrudService;

    @Operation(summary = "Создать новую задачу",
            description = "Создает новую задачу для текущего пользователя")
    @PostMapping("/task/create")
    public ResponseEntity<TaskDto> createTaskGroup(@RequestBody TaskRequestDto taskRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskCrudService.createTask(taskRequestDto));
    }

    @Operation(summary = "Обновить задачу",
            description = "Обновляет существующую задачу.")
    @PutMapping("/task/update")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity
                .ok(taskCrudService.update(taskDto.id(), taskDto));
    }

    @Operation(summary = "Получить задачу по ID",
            description = "Возвращает задачу по ее идентификатору.")
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Integer id) {
        return taskCrudService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TaskNotFoundException(
                        "По переданному id: " + id + " Задача не найдена"));
    }

    @Operation(summary = "Получить все задачи пользователя",
            description = "Возвращает все задачи текущего пользователя")
    @GetMapping("/tasks")
    public Collection<TaskDto> getAllTask() {
        return taskCrudService.getUserTasks();
    }


    @Operation(summary = "Удалить задачу",
            description = "Удаляет задачу по ее идентификатору. Пользователь может удалять только свои задачи.")
    @DeleteMapping("/task/{id}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Integer id) {
        if (taskCrudService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new TaskNotFoundException("По переданному id: " + id + " Задача не найдена");
        }
    }
}
