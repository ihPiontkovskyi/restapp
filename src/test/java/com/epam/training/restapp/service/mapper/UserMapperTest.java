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

import java.util.Arrays;
import java.util.Objects;

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

    private User userEntity;
    private UserInfo user;

    @BeforeEach
    void initData() {
        userEntity = User.builder()
                .email("Some email")
                .fullName("Some fullname")
                .id(1)
                .password("Some encrypted password")
                .posts(Arrays.asList(null, null, null))
                .build();
        user = new UserInfo();
        user.setEmail("Some email");
        user.setFullName("Some fullname");
        user.setId(1);
        user.setPassword("Some encrypted password");
        user.setPostInfos(Arrays.asList(null, null, null));
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

        UserInfo dto = userMapper.mapToDto(userEntity);

        assertAll(
                () -> assertEquals(dto.getEmail(), userEntity.getEmail()),
                () -> assertEquals(dto.getFullName(), userEntity.getFullName()),
                () -> assertEquals(dto.getPassword(), userEntity.getPassword()),
                () -> assertEquals(3, dto.getPostInfos().stream().filter(Objects::isNull).count()),
                () -> assertEquals(dto.getId(), userEntity.getId())
        );
        verify(postMapper, times(3)).mapToDto(null);
    }

    @Test
    void mapToEntityWhenCorrectDtoShouldReturnCorrectEntity() {
        when(postMapper.mapToDto(null)).thenReturn(null);

        User entity = userMapper.mapToEntity(user);

        assertAll(
                () -> assertEquals(entity.getEmail(), user.getEmail()),
                () -> assertEquals(entity.getFullName(), user.getFullName()),
                () -> assertEquals(entity.getPassword(), user.getPassword()),
                () -> assertEquals(3, entity.getPosts().stream().filter(Objects::isNull).count()),
                () -> assertEquals(entity.getId(), user.getId())
        );
        verify(postMapper, times(3)).mapToEntity(null);
    }
}

