package com.epam.training.restapp.service.mapper.impl;

import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.entity.User;
import com.epam.training.restapp.service.mapper.PostMapper;
import com.epam.training.restapp.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapperImpl implements UserMapper {
    private final PostMapper postMapper;

    @Override
    public User mapToEntity(UserInfo dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Can`t map to entity from null!");
        }
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .password(dto.getPassword())
                .posts(dto.getPostInfos()
                        .stream()
                        .map(postMapper::mapToEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserInfo mapToDto(User entity) {
        if (entity == null) {
            return null;
        }
        return UserInfo.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .password(entity.getPassword())
                .postInfos(entity.getPosts()
                        .stream()
                        .map(postMapper::mapToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
