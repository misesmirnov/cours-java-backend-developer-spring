package ru.misesmirnov.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.misesmirnov.spring.dto.StatisticsDto;
import ru.misesmirnov.spring.dto.StatusStatisticDto;
import ru.misesmirnov.spring.entity.type.TaskStatusEnum;
import ru.misesmirnov.spring.repository.TaskGroupRepository;
import ru.misesmirnov.spring.repository.TaskRepository;
import ru.misesmirnov.spring.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StatisticService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;

    public StatisticsDto getStatistics() {
        return StatisticsDto.builder()
                .totalUsers(userRepository.count())
                .totalTaskGroups(taskGroupRepository.count())
                .totalTasks(taskRepository.count())
                .tasksWithoutGroup(taskRepository.countByTaskGroupNull())
                .build();
    }

    public List<StatusStatisticDto> getTasksByStatus() {
        return Arrays.stream(TaskStatusEnum.values())
                .map(status -> StatusStatisticDto.builder()
                        .status(status)
                        .count(taskRepository.countByStatus(status))
                        .build())
                .toList();
    }
}
