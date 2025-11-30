package ru.misesmirnov.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.With;
import ru.misesmirnov.spring.entity.type.UserRoleEnum;

import java.io.Serializable;
import java.util.List;

@With
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(Integer id,
                      String username,
                      String password,
                      UserRoleEnum role,
                      List<Integer> taskGroupIds,
                      List<Integer> taskIds) implements Serializable {
}
