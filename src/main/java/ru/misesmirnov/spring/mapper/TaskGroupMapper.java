package ru.misesmirnov.spring.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.misesmirnov.spring.dto.TaskGroupDto;
import ru.misesmirnov.spring.entity.Task;
import ru.misesmirnov.spring.entity.TaskGroup;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskGroupMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "groupName")
    @Mapping(target = "taskIds", source = "tasks")
    TaskGroupDto mapToDto(TaskGroup entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "groupName")
    TaskGroup mapToEntity(TaskGroupDto dto);

    default List<Integer> toTaskIds(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getId)
                .toList();
    }
}
