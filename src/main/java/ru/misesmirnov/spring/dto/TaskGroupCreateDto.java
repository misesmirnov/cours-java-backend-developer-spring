package ru.misesmirnov.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.With;

import java.io.Serializable;
import java.util.List;

@With
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskGroupCreateDto(String groupName,
                                 Integer userId,
                                 List<Integer> taskIds) implements Serializable {
}
