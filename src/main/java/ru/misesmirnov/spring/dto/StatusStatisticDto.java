package ru.misesmirnov.spring.dto;

import lombok.Builder;
import ru.misesmirnov.spring.entity.type.TaskStatusEnum;

@Builder
public record StatusStatisticDto(TaskStatusEnum status,
                                 Long count) {
}
