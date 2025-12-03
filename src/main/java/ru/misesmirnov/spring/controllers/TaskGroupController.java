package ru.misesmirnov.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.misesmirnov.spring.dto.TaskGroupCreateDto;
import ru.misesmirnov.spring.dto.TaskGroupDto;
import ru.misesmirnov.spring.exception.TaskGroupNotFoundException;
import ru.misesmirnov.spring.service.TaskGroupCrudService;

import java.util.Collection;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class TaskGroupController {

    private final TaskGroupCrudService taskGroupCrudService;

    @PostMapping("/group/create")
    public ResponseEntity<TaskGroupDto> createTaskGroup(@RequestBody TaskGroupCreateDto taskGroupCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskGroupCrudService.createTaskGroup(taskGroupCreateDto));
    }

    @PutMapping("/group/update")
    public ResponseEntity<TaskGroupDto> updateTaskGroup(@RequestBody TaskGroupDto taskGroupDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskGroupCrudService.update(taskGroupDto.id(), taskGroupDto));
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<TaskGroupDto> getTaskGroupById(@PathVariable Integer id) {
        return taskGroupCrudService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new TaskGroupNotFoundException(
                        "По переданному id: " + id + " Группа задач не найдена"));
    }

    @GetMapping()
    public Collection<TaskGroupDto> getAllTaskGroup() {
        return taskGroupCrudService.getList();
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<TaskGroupDto> deleteTaskGroup(@PathVariable Integer id) {
        if (taskGroupCrudService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new TaskGroupNotFoundException("По переданному id: " + id + " Группа задач не найдена");
        }
    }
}
