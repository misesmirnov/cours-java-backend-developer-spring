package ru.misesmirnov.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.With;

@With
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record StatisticsDto(long totalUsers,
                            long totalTasks,
                            long totalTaskGroups,
                            long tasksWithoutGroup) {
}
