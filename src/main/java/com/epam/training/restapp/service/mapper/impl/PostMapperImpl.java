package com.epam.training.restapp.service.mapper.impl;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.entity.Post;
import com.epam.training.restapp.exception.ElementNotFoundException;
import com.epam.training.restapp.repository.UserRepository;
import com.epam.training.restapp.service.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostMapperImpl implements PostMapper {
    private final UserRepository repository;

    @Override
    public Post mapToEntity(PostInfo dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Can`t map to entity from null!");
        }
        return Post.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .createdTime(dto.getCreatedTime())
                .modifiedTime(dto.getModifiedTime())
                .description(dto.getDescription())
                .title(dto.getTitle())
                .user(repository.findById(dto.getUserId())
                        .orElseThrow(() -> new ElementNotFoundException("User id :" + dto.getUserId() + "not found!")))
                .build();
    }

    @Override
    public PostInfo mapToDto(Post entity) {
        if (entity == null) {
            return null;
        }
        return PostInfo.builder()
                .content(entity.getContent())
                .createdTime(entity.getCreatedTime())
                .description(entity.getDescription())
                .id(entity.getId())
                .modifiedTime(entity.getModifiedTime())
                .title(entity.getTitle())
                .userId(entity.getUser().getId())
                .build();
    }
}
