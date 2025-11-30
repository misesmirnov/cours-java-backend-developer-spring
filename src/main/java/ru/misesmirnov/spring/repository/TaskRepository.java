package ru.misesmirnov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.misesmirnov.spring.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
