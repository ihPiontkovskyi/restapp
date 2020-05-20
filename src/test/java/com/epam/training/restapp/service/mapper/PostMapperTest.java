package com.epam.training.restapp.service.mapper;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.entity.Post;
import com.epam.training.restapp.service.mapper.impl.PostMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.training.restapp.TestDataProvider.getPostInfoTestData;
import static com.epam.training.restapp.TestDataProvider.getPostTestData;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PostMapperTest {

    private PostMapper mapper;

    private Post testPost;
    private PostInfo testPostInfo;

    @BeforeEach
    void initData() {
        mapper = new PostMapperImpl();
        testPost = getPostTestData();
        testPostInfo = getPostInfoTestData();
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
        PostInfo dto = mapper.mapToDto(testPost);

        assertAll(
                () -> assertEquals(dto.getContent(), testPost.getContent()),
                () -> assertEquals(dto.getCreatedTime(), testPost.getCreatedTime()),
                () -> assertEquals(dto.getDescription(), testPost.getDescription()),
                () -> assertEquals(dto.getModifiedTime(), testPost.getModifiedTime()),
                () -> assertEquals(dto.getTitle(), testPost.getTitle()),
                () -> assertEquals(dto.getId(), testPost.getId())
        );
    }

    @Test
    void mapToEntityWhenCorrectDtoShouldReturnCorrectEntity() {
        Post entity = mapper.mapToEntity(testPostInfo);

        assertAll(
                () -> assertEquals(entity.getContent(), testPostInfo.getContent()),
                () -> assertEquals(entity.getCreatedTime(), testPostInfo.getCreatedTime()),
                () -> assertEquals(entity.getDescription(), testPostInfo.getDescription()),
                () -> assertEquals(entity.getModifiedTime(), testPostInfo.getModifiedTime()),
                () -> assertEquals(entity.getTitle(), testPostInfo.getTitle()),
                () -> assertEquals(entity.getId(), testPostInfo.getId())
        );
    }
}
