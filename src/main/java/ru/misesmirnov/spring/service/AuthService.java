package ru.misesmirnov.spring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.misesmirnov.spring.dto.LoginRequest;
import ru.misesmirnov.spring.dto.LoginResponse;
import ru.misesmirnov.spring.entity.User;
import ru.misesmirnov.spring.repository.UserRepository;
import ru.misesmirnov.spring.utils.JwtTokenProvider;

@Service
@AllArgsConstructor
public class AuthService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final JwtTokenProvider jwtTokenProvider;
    final SecurityContextService securityContextService;

    public LoginResponse auth(LoginRequest loginRequest) {
        User user = userRepository.findByEmailIgnoreCase(loginRequest.email())
                .orElseThrow(() -> new BadCredentialsException("Пользователь не найден"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Неверный пароль");
        }

        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    @Named("getCurrentUserId")
    public Integer getCurrentUserId() {
        return getCurrentUser().getId();
    }

    @Named("getCurrentUser")
    public User getCurrentUser() {
        String currentUsername = securityContextService.getCurrentUsername();
        return userRepository.findByUsernameIgnoreCase(currentUsername)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь: " + currentUsername + " не найден"));
    }
}
