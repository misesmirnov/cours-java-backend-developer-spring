package ru.misesmirnov.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.misesmirnov.spring.dto.TaskCreateDto;
import ru.misesmirnov.spring.dto.TaskDto;
import ru.misesmirnov.spring.exception.TaskNotFoundException;
import ru.misesmirnov.spring.service.TaskCrudService;

import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskCrudService taskCrudService;

    @PostMapping("/task/create")
    public ResponseEntity<TaskDto> createTaskGroup(@RequestBody TaskCreateDto taskCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskCrudService.createTask(taskCreateDto));
    }

    @PutMapping("/task/update")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskCrudService.update(taskDto.id(), taskDto));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Integer id) {
        return taskCrudService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TaskNotFoundException(
                        "По переданному id: " + id + " Задача не найдена"));
    }

    @GetMapping("/task/getAllTask")
    public Collection<TaskDto> getAllTask() {
        return taskCrudService.getList();
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<TaskDto> deleteTask(@PathVariable Integer id) {
        if (taskCrudService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new TaskNotFoundException("По переданному id: " + id + " Задача не найдена");
        }
    }
}
