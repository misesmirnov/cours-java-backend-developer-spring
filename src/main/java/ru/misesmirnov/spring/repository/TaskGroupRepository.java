package ru.misesmirnov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.misesmirnov.spring.entity.TaskGroup;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Integer> {

    Optional<TaskGroup> findByIdAndUser_Id(Integer id, Integer userId);

    List<TaskGroup> findByUser_Id(Integer id);
}
