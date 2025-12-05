package ru.misesmirnov.spring.mapper;

import org.mapstruct.*;
import ru.misesmirnov.spring.dto.TaskGroupDto;
import ru.misesmirnov.spring.dto.TaskGroupRequestDto;
import ru.misesmirnov.spring.entity.Task;
import ru.misesmirnov.spring.entity.TaskGroup;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskGroupMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "groupName")
    @Mapping(target = "taskIds", source = "tasks")
    @Mapping(target = "userId", source = "user.id")
    TaskGroupDto mapToDto(TaskGroup entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "groupName")
    TaskGroup mapToEntity(TaskGroupDto dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "groupName")
    TaskGroupDto mapToDto(TaskGroupRequestDto taskGroupRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaskGroup updateEntity(TaskGroup source, @MappingTarget TaskGroup target);

    default List<Integer> toTaskIds(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getId)
                .toList();
    }
}
