package com.epam.training.restapp.controller;

import com.epam.training.restapp.dto.UserInfo;
import com.epam.training.restapp.service.UserService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Objects;

import static com.epam.training.restapp.TestDataProvider.getUserInfoPassedData;
import static com.epam.training.restapp.TestDataProvider.getUserInfoTestData;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    private UserInfo testUserInfo;
    private UserInfo passedUserInfo;

    @BeforeEach
    void intiData() {
        testUserInfo = getUserInfoTestData();
        passedUserInfo = getUserInfoPassedData();
    }

    @AfterEach
    void resetData() {
        reset(service);
    }

    @Test
    void getAllPostShouldReturnDataSuccessfully() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<UserInfo> page = new PageImpl<>(Collections.singletonList(testUserInfo), pageable, 1);
        when(service.getAll(pageable)).thenReturn(page);

        Page<UserInfo> userInfos = controller.getAllUsers(pageable);

        assertEquals(page, userInfos);
        verify(service).getAll(pageable);
    }

    @Test
    void getAllPostShouldReturnEmptyPage() {
        Pageable pageable = Mockito.mock(Pageable.class);
        when(service.getAll(pageable)).thenReturn(Page.empty());

        Page<UserInfo> userInfos = controller.getAllUsers(pageable);

        assertEquals(Page.empty(), userInfos);
        verify(service).getAll(pageable);
    }

    @Test
    void createPostShouldReturnDataSuccessfully() {
        when(service.create(testUserInfo)).thenReturn(passedUserInfo);

        ResponseEntity<UserInfo> userResponse = controller.createUser(testUserInfo);
        final UserInfo result = userResponse.getBody();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.CREATED, userResponse.getStatusCode()),
                () -> assertEquals(passedUserInfo.getId(), result.getId()),
                () -> assertEquals(passedUserInfo.getPassword(), result.getPassword()),
                () -> assertEquals(passedUserInfo.getFullName(), result.getFullName()),
                () -> assertEquals(passedUserInfo.getEmail(), result.getEmail()),
                () -> assertEquals(2, result.getPostInfos().stream().filter(Objects::isNull).count())
        );
        verify(service).create(testUserInfo);
    }

    @Test
    void updatePostShouldReturnDataSuccessfully() {
        when(service.update(testUserInfo.getId(), testUserInfo)).thenReturn(passedUserInfo);

        ResponseEntity<UserInfo> userResponse = controller.updateUser(testUserInfo.getId(), testUserInfo);
        final UserInfo result = userResponse.getBody();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(HttpStatus.OK, userResponse.getStatusCode()),
                () -> assertEquals(passedUserInfo.getId(), result.getId()),
                () -> assertEquals(passedUserInfo.getPassword(), result.getPassword()),
                () -> assertEquals(passedUserInfo.getFullName(), result.getFullName()),
                () -> assertEquals(passedUserInfo.getEmail(), result.getEmail()),
                () -> assertEquals(2, result.getPostInfos().stream().filter(Objects::isNull).count())
        );
        verify(service).update(testUserInfo.getId(), testUserInfo);
    }

    @Test
    void deleteShouldInvokeDeleteOnService() {

        final ResponseEntity<?> response = controller.deleteUser(testUserInfo.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(service).delete(testUserInfo.getId());
    }
}
