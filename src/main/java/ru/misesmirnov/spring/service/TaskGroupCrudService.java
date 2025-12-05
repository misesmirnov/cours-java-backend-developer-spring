package ru.misesmirnov.spring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.misesmirnov.spring.dto.TaskDto;
import ru.misesmirnov.spring.dto.TaskGroupDto;
import ru.misesmirnov.spring.dto.TaskGroupRequestDto;
import ru.misesmirnov.spring.entity.TaskGroup;
import ru.misesmirnov.spring.entity.User;
import ru.misesmirnov.spring.exception.TaskGroupHaveTaskOtherUserException;
import ru.misesmirnov.spring.exception.TaskGroupNotFoundException;
import ru.misesmirnov.spring.mapper.TaskGroupMapper;
import ru.misesmirnov.spring.repository.TaskGroupRepository;
import ru.misesmirnov.spring.repository.TaskRepository;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class TaskGroupCrudService implements CrudService<TaskGroupDto> {

    private final TaskGroupRepository taskGroupRepository;
    private final TaskRepository taskRepository;
    private final TaskGroupMapper taskGroupMapper;
    private final UserCrudService userCrudService;
    private final AuthService authService;

    @Override
    public Optional<TaskGroupDto> getById(Integer id) {
        log.info("Запрос на получение Группы Задач по Id: {}", id);
        return taskGroupRepository.findByIdAndUser_Id(id, authService.getCurrentUserId())
                .map(taskGroupMapper::mapToDto);
    }

    public Optional<TaskGroupDto> getByIdForAdmin(Integer id) {
        log.info("Запрос на получение Группы Задач по Id: {}", id);
        return taskGroupRepository.findById(id)
                .map(taskGroupMapper::mapToDto);
    }

    @Override
    public Collection<TaskGroupDto> getList() {
        log.info("Запрос на получение списка Групп Задач");
        return taskGroupRepository.findAll().stream()
                .map(taskGroupMapper::mapToDto)
                .toList();
    }

    public Collection<TaskGroupDto> getUserTaskGroup() {
        return getUserTaskGroup(authService.getCurrentUserId());
    }

    public Collection<TaskGroupDto> getUserTaskGroup(Integer UserId) {
        log.info("Запрос на получение списка Групп Задач по UserId: " + UserId);
        return taskGroupRepository.findByUser_Id(UserId).stream()
                .map(taskGroupMapper::mapToDto)
                .toList();
    }

    public TaskGroupDto createTaskGroup(TaskGroupRequestDto taskGroupCreateDto) {
        return create(taskGroupMapper.mapToDto(taskGroupCreateDto)
                .withUserId(authService.getCurrentUserId()));
    }

    @Override
    public TaskGroupDto create(TaskGroupDto item) {
        log.info("Запрос на создание Группы Задач Create");
        //Поиск пользователя (обязательное поле)
        User user = userCrudService.findUserById(item.userId());
        //Устанавливаем юзера в группу задач перед сохранением
        TaskGroup taskGroup = taskGroupMapper.mapToEntity(item);
        taskGroup.setUser(user);
        //сохраняем в БД
        taskGroup = taskGroupRepository.save(taskGroup);
        return taskGroupMapper.mapToDto(taskGroup);
    }

    @Override
    public TaskGroupDto update(Integer id, TaskGroupDto item) {
        log.info("Запрос на обновление Группы Задач по ID: {}", id);
        TaskGroup target = findTaskGroupById(id, authService.getCurrentUserId());
        TaskGroup updated = taskGroupMapper.updateEntity(taskGroupMapper.mapToEntity(item), target);

        if (item.userId() != null && !item.userId().equals(target.getUser().getId())) {
            if (countByTaskGroupId(target.getId()) > 0) {
                throw new TaskGroupHaveTaskOtherUserException("Изменение Пользователя возможно только для пустых Групп");
            }
            User user = userCrudService.findUserById(item.userId());
            updated.setUser(user);
        }
        return taskGroupMapper.mapToDto(taskGroupRepository.save(updated));
    }

    @Override
    public boolean delete(Integer id) {
        log.info("Запрос на delete группы Задач с id: {}", id);
        if (taskGroupRepository.existsById(id)) {
            taskGroupRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TaskGroup getTaskGroup(TaskDto taskDto, User user) {
        TaskGroup taskGroup = null;
        if (taskDto.taskGroupId() != null) {
            taskGroup = findTaskGroupById(taskDto.taskGroupId(), authService.getCurrentUserId());
            if (!taskGroup.getUser().getId().equals(user.getId())) {
                throw new TaskGroupNotFoundException("Группа задач не принадлежит пользователю");
            }
        }
        return taskGroup;
    }

    public TaskGroup findTaskGroupById(Integer id, Integer userId) {
        return taskGroupRepository.findByIdAndUser_Id(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Группа задач с ID " + id + " не найдена"));
    }

    public long countByTaskGroupId(Integer taskGroupId) {
        return taskRepository.countByTaskGroup_Id(taskGroupId);
    }
}
