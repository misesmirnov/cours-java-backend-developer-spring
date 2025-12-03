package ru.misesmirnov.spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.misesmirnov.spring.entity.type.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "username", nullable = false)
    String username;

    @Column(name = "email", nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    UserRoleEnum role;

    @Column(name = "password", nullable = false)
    String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskGroup> taskGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> tasks = new ArrayList<>();
}
