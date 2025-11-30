package ru.misesmirnov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.misesmirnov.spring.entity.TaskGroup;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Integer> {
}
