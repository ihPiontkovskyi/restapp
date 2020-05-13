package com.epam.training.restapp.service.mapper.impl;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.entity.Post;
import com.epam.training.restapp.service.mapper.PostMapper;
import com.epam.training.restapp.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostMapperImpl implements PostMapper {

    private final UserMapper userMapper;

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
                .user(userMapper.mapToEntity(dto.getUserInfo()))
                .build();
    }

    @Override
    public PostInfo mapToDto(Post entity) {
        if (entity == null) {
            return null;
        }
        PostInfo dto = new PostInfo();
        dto.setContent(entity.getContent());
        dto.setCreatedTime(entity.getCreatedTime());
        dto.setDescription(entity.getDescription());
        dto.setId(entity.getId());
        dto.setModifiedTime(entity.getModifiedTime());
        dto.setTitle(entity.getTitle());
        dto.setUserInfo(userMapper.mapToDto(entity.getUser()));
        return dto;
    }
}
