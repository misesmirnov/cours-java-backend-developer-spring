package ru.misesmirnov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.misesmirnov.spring.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);
}
