package ru.misesmirnov.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.misesmirnov.spring.entity.type.TaskStatusEnum;

@Entity
@Setter
@Getter
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    TaskStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_group_id")
    TaskGroup taskGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
