package ru.misesmirnov.spring.mapper;

import org.mapstruct.*;
import ru.misesmirnov.spring.dto.TaskDto;
import ru.misesmirnov.spring.dto.TaskRequestDto;
import ru.misesmirnov.spring.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "status")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "taskGroupId", source = "taskGroup.id")
    TaskDto mapToDto(Task entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "status")
    Task mapToEntity(TaskDto dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "description")
    @Mapping(target = "status")
    @Mapping(target = "taskGroupId")
    TaskDto mapToDto(TaskRequestDto taskRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task updateEntity(Task source, @MappingTarget Task target);
}
