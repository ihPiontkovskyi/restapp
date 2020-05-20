package com.epam.training.restapp.service.mapper;

import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.entity.User;
import com.epam.training.restapp.service.mapper.impl.UserMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static com.epam.training.restapp.TestDataProvider.getUserInfoTestData;
import static com.epam.training.restapp.TestDataProvider.getUserTestData;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private UserMapperImpl userMapper;

    private User testUser;
    private UserInfo testUserInfo;

    @BeforeEach
    void initData() {
        testUser = getUserTestData();
        testUserInfo = getUserInfoTestData();
    }

    @AfterEach
    void resetData() {
        reset(postMapper);
    }

    @Test
    void mapToDtoWhenEntityIsNullDShouldReturnNull() {
        assertNull(userMapper.mapToDto(null));
    }

    @Test
    void mapToEntityWhenDtoIsNullDShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userMapper.mapToEntity(null), "Can`t map to entity from null!");
    }

    @Test
    void mapToDtoWhenCorrectEntityShouldReturnCorrectDto() {
        when(postMapper.mapToDto(null)).thenReturn(null);

        UserInfo dto = userMapper.mapToDto(testUser);

        assertAll(
                () -> assertEquals(dto.getEmail(), testUser.getEmail()),
                () -> assertEquals(dto.getFullName(), testUser.getFullName()),
                () -> assertEquals(dto.getPassword(), testUser.getPassword()),
                () -> assertEquals(3, dto.getPostInfos().stream().filter(Objects::isNull).count()),
                () -> assertEquals(dto.getId(), testUser.getId())
        );
        verify(postMapper, times(3)).mapToDto(null);
    }

    @Test
    void mapToEntityWhenCorrectDtoShouldReturnCorrectEntity() {
        when(postMapper.mapToDto(null)).thenReturn(null);

        User entity = userMapper.mapToEntity(testUserInfo);

        assertAll(
                () -> assertEquals(entity.getEmail(), testUserInfo.getEmail()),
                () -> assertEquals(entity.getFullName(), testUserInfo.getFullName()),
                () -> assertEquals(entity.getPassword(), testUserInfo.getPassword()),
                () -> assertEquals(3, entity.getPosts().stream().filter(Objects::isNull).count()),
                () -> assertEquals(entity.getId(), testUserInfo.getId())
        );
        verify(postMapper, times(3)).mapToEntity(null);
    }
}

