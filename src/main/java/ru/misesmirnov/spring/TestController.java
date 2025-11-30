package ru.misesmirnov.spring;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Test API", description = "Тестовые endpoints")
public class TestController {
    @GetMapping("/test")
    @Operation(summary = "Тестовый endpoint")
    public String test() {
        return "Hello from Spring Boot!";
    }

    @GetMapping("/health")
    @Operation(summary = "Health check")
    public String health() {
        return "OK";
    }
}
