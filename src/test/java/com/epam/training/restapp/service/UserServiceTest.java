package com.epam.training.restapp.service;

import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.entity.User;
import com.epam.training.restapp.exception.ElementNotFoundException;
import com.epam.training.restapp.repository.UserRepository;
import com.epam.training.restapp.service.impl.UserServiceImpl;
import com.epam.training.restapp.service.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static com.epam.training.restapp.TestDataProvider.getUserInfoPassedData;
import static com.epam.training.restapp.TestDataProvider.getUserInfoTestData;
import static com.epam.training.restapp.TestDataProvider.getUserPassedData;
import static com.epam.training.restapp.TestDataProvider.getUserTestData;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl service;

    private UserInfo testUserInfo;
    private UserInfo passedUserInfo;
    private User testUser;
    private User passedUser;

    @BeforeEach
    void intiData() {
        testUserInfo = getUserInfoTestData();
        passedUserInfo = getUserInfoPassedData();
        testUser = getUserTestData();
        passedUser = getUserPassedData();
    }

    @AfterEach
    void resetData() {
        reset(repository, mapper, encoder);
    }

    @Test
    void createShouldReturnTestPostInfoData() {
        when(mapper.mapToEntity(testUserInfo)).thenReturn(testUser);
        when(repository.save(testUser)).thenReturn(passedUser);
        when(mapper.mapToDto(passedUser)).thenReturn(passedUserInfo);
        String password = testUserInfo.getPassword();
        when(encoder.encode(password)).thenReturn("<encrypted>");

        UserInfo result = service.create(testUserInfo);

        assertAll(
                () -> assertEquals(passedUserInfo.getId(), result.getId()),
                () -> assertEquals(passedUserInfo.getPassword(), result.getPassword()),
                () -> assertEquals(passedUserInfo.getFullName(), result.getFullName()),
                () -> assertEquals(passedUserInfo.getEmail(), result.getEmail()),
                () -> assertEquals(2, result.getPostInfos().stream().filter(Objects::isNull).count())
        );
        verify(mapper).mapToEntity(testUserInfo);
        verify(repository).save(testUser);
        verify(mapper).mapToDto(passedUser);
        verify(encoder).encode(password);
    }

    @Test
    void findAllShouldReturnEmptyPage() {
        Pageable pageable = Mockito.mock(Pageable.class);
        when(repository.findAll(pageable)).thenReturn(Page.empty());

        Page<UserInfo> result = service.getAll(pageable);

        assertEquals(Page.empty(), result);
        verify(repository).findAll(pageable);
    }

    @Test
    void findAllShouldReturnPageWith1Element() {
        Pageable pageable = Mockito.mock(Pageable.class);
        final PageImpl<User> page = new PageImpl<>(Collections.singletonList(passedUser), pageable, 1);
        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.mapToDto(passedUser)).thenReturn(passedUserInfo);

        Page<UserInfo> result = service.getAll(pageable);

        assertEquals(1, result.getSize());
        verify(repository).findAll(pageable);
        verify(mapper).mapToDto(passedUser);
    }

    @Test
    void deleteShouldThrowElementNotFoundException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> service.delete(1), "User id : 1 not found!");

        verify(repository).findById(1);
    }

    @Test
    void deleteShouldDeleteElementSuccessfully() {
        when(repository.findById(1)).thenReturn(Optional.of(testUser));

        service.delete(1);

        verify(repository).findById(1);
        verify(repository).delete(testUser);
    }

    @Test
    void updateShouldThrowElementNotFoundException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> service.update(1, testUserInfo), "User id : 1 not found!");

        verify(repository).findById(1);
    }

    @Test
    void updateShouldUpdateElementSuccessfully() {
        when(repository.findById(1)).thenReturn(Optional.of(testUser));
        when(mapper.mapToDto(testUser)).thenReturn(passedUserInfo);
        when(mapper.mapToEntity(passedUserInfo)).thenReturn(passedUser);
        when(repository.save(passedUser)).thenReturn(passedUser);
        UserInfo emptyPostInfo = UserInfo.builder().build();
        when(mapper.mapToDto(passedUser)).thenReturn(emptyPostInfo);
        when(encoder.matches(passedUserInfo.getPassword(), testUserInfo.getPassword())).thenReturn(false);

        UserInfo result = service.update(1, testUserInfo);

        assertAll(
                () -> assertNull(result.getEmail()),
                () -> assertNull(result.getFullName()),
                () -> assertNull(result.getPassword()),
                () -> assertNull(result.getPostInfos()),
                () -> assertNull(result.getId())
        );
        verify(repository).findById(1);
        verify(mapper).mapToDto(testUser);
        verify(mapper).mapToEntity(passedUserInfo);
        verify(repository).save(passedUser);
        verify(mapper).mapToDto(passedUser);
        verify(encoder).matches(passedUserInfo.getPassword(), testUserInfo.getPassword());
    }
}
