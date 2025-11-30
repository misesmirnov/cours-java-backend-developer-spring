package ru.misesmirnov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.misesmirnov.spring.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
