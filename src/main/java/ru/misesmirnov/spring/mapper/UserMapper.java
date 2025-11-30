package ru.misesmirnov.spring.mapper;

import org.mapstruct.*;
import ru.misesmirnov.spring.dto.UserDto;
import ru.misesmirnov.spring.entity.Task;
import ru.misesmirnov.spring.entity.TaskGroup;
import ru.misesmirnov.spring.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "role")
    @Mapping(target = "username")
    @Mapping(target = "taskGroupIds", source = "taskGroups")
    @Mapping(target = "taskIds", source = "tasks")
    UserDto mapToDto(User entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "role")
    @Mapping(target = "username")
    @Mapping(target = "password")
    User mapToEntity(UserDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "role")
//    @Mapping(target = "username")
    User updateEntity(User source, @MappingTarget User target);

    default List<Integer> toTaskIds(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getId)
                .toList();
    }

    default List<Integer> toTaskGroupIds(List<TaskGroup> tasks) {
        return tasks.stream()
                .map(TaskGroup::getId)
                .toList();
    }
}
