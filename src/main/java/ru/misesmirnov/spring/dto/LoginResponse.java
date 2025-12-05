package ru.misesmirnov.spring.dto;

import lombok.Builder;
import ru.misesmirnov.spring.entity.type.UserRoleEnum;

@Builder
public record LoginResponse(String token,
                            String username,
                            UserRoleEnum role) {
}
