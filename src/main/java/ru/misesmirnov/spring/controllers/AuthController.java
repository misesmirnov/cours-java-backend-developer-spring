package ru.misesmirnov.spring.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.misesmirnov.spring.dto.UserDto;
import ru.misesmirnov.spring.service.UserCrudService;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class AuthController {

    private final UserCrudService userCrudService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userCrudService.create(userDto));
    }


}
