package com.epam.training.restapp.service.mapper;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.entity.Post;
import com.epam.training.restapp.service.mapper.impl.PostMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostMapperTest {

    private static final LocalDateTime CREATED_TIME = LocalDateTime.of(2020, 5, 13, 14, 0, 0);
    private static final LocalDateTime TIME_NOW = LocalDateTime.now();
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private PostMapperImpl mapper;

    private Post postEntity;
    private PostInfo post;

    @BeforeEach
    void initData() {
        postEntity = Post.builder()
                .content("Some text")
                .createdTime(CREATED_TIME)
                .description("Some description")
                .id(1)
                .modifiedTime(TIME_NOW)
                .title("Some title")
                .user(null)
                .build();
        post = new PostInfo();
        post.setContent("Some text");
        post.setCreatedTime(CREATED_TIME);
        post.setDescription("Some description");
        post.setModifiedTime(TIME_NOW);
        post.setTitle("Some title");
        post.setUserInfo(null);
        post.setId(1);
    }

    @AfterEach
    void resetData() {
        reset(userMapper);
    }

    @Test
    void mapToDtoWhenEntityIsNullDShouldReturnNull() {
        assertNull(mapper.mapToDto(null));
    }

    @Test
    void mapToEntityWhenDtoIsNullDShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> mapper.mapToEntity(null), "Can`t map to entity from null!");
    }

    @Test
    void mapToDtoWhenCorrectEntityShouldReturnCorrectDto() {
        when(userMapper.mapToDto(null)).thenReturn(null);

        PostInfo dto = mapper.mapToDto(postEntity);

        assertAll(
                () -> assertEquals(dto.getContent(), postEntity.getContent()),
                () -> assertEquals(dto.getCreatedTime(), postEntity.getCreatedTime()),
                () -> assertEquals(dto.getDescription(), postEntity.getDescription()),
                () -> assertEquals(dto.getModifiedTime(), postEntity.getModifiedTime()),
                () -> assertEquals(dto.getTitle(), postEntity.getTitle()),
                () -> assertEquals(dto.getId(), postEntity.getId()),
                () -> assertNull(dto.getUserInfo())
        );
        verify(userMapper).mapToDto(null);
    }

    @Test
    void mapToEntityWhenCorrectDtoShouldReturnCorrectEntity() {
        when(userMapper.mapToEntity(null)).thenReturn(null);

        Post entity = mapper.mapToEntity(post);

        assertAll(
                () -> assertEquals(entity.getContent(), post.getContent()),
                () -> assertEquals(entity.getCreatedTime(), post.getCreatedTime()),
                () -> assertEquals(entity.getDescription(), post.getDescription()),
                () -> assertEquals(entity.getModifiedTime(), post.getModifiedTime()),
                () -> assertEquals(entity.getTitle(), post.getTitle()),
                () -> assertEquals(entity.getId(), post.getId()),
                () -> assertNull(entity.getUser())
        );
        verify(userMapper).mapToEntity(null);
    }
}
