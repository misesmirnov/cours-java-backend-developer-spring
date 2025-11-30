package ru.misesmirnov.spring.service;

import java.util.Collection;
import java.util.Optional;

public interface CrudService<DTO> {

    Optional<DTO> getById(Integer id);

    Collection<DTO> getList();

    DTO create(DTO item);

    DTO update(Integer id, DTO item);

    boolean delete(Integer id);
}
