package ru.misesmirnov.spring.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.misesmirnov.spring.dto.TaskDto;
import ru.misesmirnov.spring.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "status")
    TaskDto mapToDto(Task entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "status")
    Task mapToEntity(TaskDto dto);
}
