package ru.misesmirnov.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.misesmirnov.spring.dto.LoginRequest;
import ru.misesmirnov.spring.dto.LoginResponse;
import ru.misesmirnov.spring.dto.RegisterUserDto;
import ru.misesmirnov.spring.dto.UserDto;
import ru.misesmirnov.spring.service.AuthService;
import ru.misesmirnov.spring.service.UserCrudService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "API для регистрации и аутентификации пользователей")
public class AuthController {

    private final AuthService authService;
    private final UserCrudService userCrudService;

    @Operation(summary = "Регистрация нового пользователя",
            description = "Создает нового пользователя в системе. Пароль автоматически шифруется.")
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userCrudService.registerUser(registerUserDto));
    }

    @Operation(summary = "Аутентификация пользователя",
            description = "Проверяет учетные данные и возвращает JWT токен для доступа к API")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.auth(request));
    }
}
