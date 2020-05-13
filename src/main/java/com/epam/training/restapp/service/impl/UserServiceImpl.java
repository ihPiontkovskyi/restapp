package com.epam.training.restapp.service.impl;

import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.exception.ElementNotFoundException;
import com.epam.training.restapp.repository.UserRepository;
import com.epam.training.restapp.service.UserService;
import com.epam.training.restapp.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public Page<UserInfo> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::mapToDto);
    }

    @Override
    public UserInfo create(UserInfo dto) {
        return mapper.mapToDto(repository.save(mapper.mapToEntity(dto)));
    }

    @Override
    public UserInfo update(Integer id, UserInfo dto) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .map(e -> {
                    e.setEmail(dto.getEmail());
                    e.setFullName(dto.getFullName());
                    e.setPassword(dto.getPassword());
                    e.setPostInfos(dto.getPostInfos());
                    return mapper.mapToDto(repository.save(mapper.mapToEntity(e)));
                }).orElseThrow(() -> new ElementNotFoundException("Post id :" + id + "not found!"));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
