package ru.misesmirnov.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.misesmirnov.spring.entity.Task;
import ru.misesmirnov.spring.entity.type.TaskStatusEnum;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    long countByTaskGroup_Id(Integer id);

    @Query("select count(t) from Task t where t.taskGroup is null")
    long countByTaskGroupNull();

    @Query("select count(t) from Task t where t.status = ?1")
    long countByStatus(TaskStatusEnum status);

    List<Task> findByUser_Id(Integer id);

    Optional<Task> findByIdAndUser_Id(Integer id, Integer userId);

    boolean existsByIdAndUser_Id(Integer id, Integer userId);
}
