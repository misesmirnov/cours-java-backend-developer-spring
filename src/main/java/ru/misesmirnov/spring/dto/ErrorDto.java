package ru.misesmirnov.spring.dto;

import lombok.Builder;

@Builder
public record ErrorDto(String message) {
}
