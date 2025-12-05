package ru.misesmirnov.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.With;
import ru.misesmirnov.spring.entity.type.TaskStatusEnum;

import java.io.Serializable;

@With
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskRequestDto(String title,
                             String description,
                             TaskStatusEnum status,
                             Integer taskGroupId) implements Serializable {
}
