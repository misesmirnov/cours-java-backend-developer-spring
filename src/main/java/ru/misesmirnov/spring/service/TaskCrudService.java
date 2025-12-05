package ru.misesmirnov.spring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.misesmirnov.spring.dto.TaskDto;
import ru.misesmirnov.spring.dto.TaskRequestDto;
import ru.misesmirnov.spring.entity.Task;
import ru.misesmirnov.spring.entity.TaskGroup;
import ru.misesmirnov.spring.entity.User;
import ru.misesmirnov.spring.mapper.TaskMapper;
import ru.misesmirnov.spring.repository.TaskRepository;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class TaskCrudService implements CrudService<TaskDto> {

    final UserCrudService userCrudService;
    final TaskGroupCrudService taskGroupCrudService;
    final TaskRepository taskRepository;
    final TaskMapper taskMapper;
    final AuthService authService;

    @Override
    public Optional<TaskDto> getById(Integer id) {
        log.info("Запрос на получение Задачи по Id: {}", id);
        return taskRepository.findByIdAndUser_Id(id, authService.getCurrentUserId())
                .map(taskMapper::mapToDto);
    }

    public Optional<TaskDto> getByIdForAdmin(Integer id) {
        log.info("Запрос на получение Задачи по Id: {}", id);
        return taskRepository.findById(id)
                .map(taskMapper::mapToDto);
    }

    @Override
    public Collection<TaskDto> getList() {
        log.info("Запрос на получение списка Задач");
        return taskRepository.findAll().stream()
                .map(taskMapper::mapToDto)
                .toList();
    }

    public Collection<TaskDto> getUserTasks() {
        return getUserTasks(authService.getCurrentUserId());
    }

    public Collection<TaskDto> getUserTasks(Integer UserId) {
        log.info("Запрос на получение списка Задач по UserId: " + UserId);
        return taskRepository.findByUser_Id(UserId).stream()
                .map(taskMapper::mapToDto)
                .toList();
    }

    public TaskDto createTask(TaskRequestDto taskRequestDto) {
        return create(taskMapper.mapToDto(taskRequestDto)
                .withUserId(authService.getCurrentUserId()));
    }

    @Override
    public TaskDto create(TaskDto item) {
        log.info("Запрос на создание Задачи Create");
        //Поиск пользователя (обязательное поле)
        User user = userCrudService.findUserById(item.userId());
        //Поиск Группы задач если передали
        TaskGroup taskGroup = taskGroupCrudService.getTaskGroup(item, user);
        //Устанавливаем юзера и группу в задачу перед сохранением
        Task task = taskMapper.mapToEntity(item);
        task.setUser(user);
        task.setTaskGroup(taskGroup);
        //сохраняем в БД
        task = taskRepository.save(task);
        return taskMapper.mapToDto(task);
    }

    @Override
    public TaskDto update(Integer id, TaskDto item) {
        log.info("Запрос на обновление Задачи по ID: {}", id);
        Task target = findTaskById(id, authService.getCurrentUserId());
        Task updated = taskMapper.updateEntity(taskMapper.mapToEntity(item), target);

        if (item.userId() != null) {
            User user = userCrudService.findUserById(item.userId());
            updated.setUser(user);
        }

        if (item.taskGroupId() != null) {
            TaskGroup newGroup = taskGroupCrudService.getTaskGroup(item, updated.getUser());
            updated.setTaskGroup(newGroup);
        } else {
            // Очищаем группу, если явно не указано
            updated.setTaskGroup(null);
        }
        return taskMapper.mapToDto(taskRepository.save(updated));
    }

    @Override
    public boolean delete(Integer id) {
        log.info("Запрос на delete Задачи с id: {}", id);
        if (taskRepository.existsByIdAndUser_Id(id, authService.getCurrentUserId())) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Task findTaskById(Integer id, Integer userId) {
        return taskRepository.findByIdAndUser_Id(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Задача с ID " + id + " не найдена"));
    }
}
