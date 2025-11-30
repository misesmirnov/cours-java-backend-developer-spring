package ru.misesmirnov.spring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.misesmirnov.spring.dto.UserDto;
import ru.misesmirnov.spring.entity.User;
import ru.misesmirnov.spring.mapper.UserMapper;
import ru.misesmirnov.spring.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserCrudService implements CrudService<UserDto> {

    final UserRepository userRepository;
    final UserMapper userMapper;

    @Override
    public Optional<UserDto> getById(Integer id) {
        log.info("Запрос на получение Пользователя по Id: {}", id);
        return userRepository.findById(id)
                .map(userMapper::mapToDto);
    }

    @Override
    public Collection<UserDto> getList() {
        log.info("Запрос на получение списка Пользователей");
        return userRepository.findAll().stream()
                .map(userMapper::mapToDto)
                .toList();
    }

    @Override
    public UserDto create(UserDto item) {
        log.info("Запрос на создание Пользователя Create");
        User user = userRepository.save(userMapper.mapToEntity(item));
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto update(Integer id, UserDto item) {
        log.info("Запрос на обновление Пользователя {}", id);
        User target = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ID " + id + " не найден"));
        User updated = userMapper.updateEntity(userMapper.mapToEntity(item), target);
        return userMapper.mapToDto(userRepository.save(updated));
    }

    @Override
    public boolean delete(Integer id) {
        log.info("Запрос на delete Пользователя с id: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
