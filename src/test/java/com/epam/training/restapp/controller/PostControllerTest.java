package com.epam.training.restapp.controller;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.service.PostService;
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

import static com.epam.training.restapp.TestDataProvider.getPostInfoPassedData;
import static com.epam.training.restapp.TestDataProvider.getPostInfoTestData;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
    @Mock
    private PostService service;

    @InjectMocks
    private PostController controller;

    private PostInfo testPostInfo;
    private PostInfo passedPostInfo;

    @BeforeEach
    void intiData() {
        testPostInfo = getPostInfoTestData();
        passedPostInfo = getPostInfoPassedData();
    }

    @AfterEach
    void resetData() {
        reset(service);
    }

    @Test
    void getAllPostShouldReturnDataSuccessfully() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<PostInfo> page = new PageImpl<>(Collections.singletonList(testPostInfo), pageable, 1);
        when(service.getAll(pageable)).thenReturn(page);

        Page<PostInfo> allPosts = controller.getAllPosts(pageable);

        assertEquals(page, allPosts);
        verify(service).getAll(pageable);
    }

    @Test
    void getAllPostShouldReturnEmptyPage() {
        Pageable pageable = Mockito.mock(Pageable.class);
        when(service.getAll(pageable)).thenReturn(Page.empty());

        Page<PostInfo> allPosts = controller.getAllPosts(pageable);

        assertEquals(Page.empty(), allPosts);
        verify(service).getAll(pageable);
    }

    @Test
    void createPostShouldReturnDataSuccessfully() {
        when(service.create(testPostInfo)).thenReturn(passedPostInfo);

        ResponseEntity<PostInfo> responsePost = controller.createPost(testPostInfo);
        final PostInfo post = responsePost.getBody();

        assertAll(
                () -> assertNotNull(post),
                () -> assertEquals(HttpStatus.CREATED, responsePost.getStatusCode()),
                () -> assertEquals(passedPostInfo.getTitle(), post.getTitle()),
                () -> assertEquals(passedPostInfo.getId(), post.getId()),
                () -> assertEquals(passedPostInfo.getModifiedTime(), post.getModifiedTime()),
                () -> assertEquals(passedPostInfo.getDescription(), post.getDescription()),
                () -> assertEquals(passedPostInfo.getContent(), post.getContent()),
                () -> assertEquals(passedPostInfo.getCreatedTime(), post.getCreatedTime())
        );
        verify(service).create(testPostInfo);
    }

    @Test
    void updatePostShouldReturnDataSuccessfully() {
        when(service.update(testPostInfo.getId(), testPostInfo)).thenReturn(passedPostInfo);

        ResponseEntity<PostInfo> responsePost = controller.updatePost(testPostInfo.getId(), testPostInfo);
        final PostInfo post = responsePost.getBody();

        assertAll(
                () -> assertNotNull(post),
                () -> assertEquals(HttpStatus.OK, responsePost.getStatusCode()),
                () -> assertEquals(passedPostInfo.getTitle(), post.getTitle()),
                () -> assertEquals(passedPostInfo.getId(), post.getId()),
                () -> assertEquals(passedPostInfo.getModifiedTime(), post.getModifiedTime()),
                () -> assertEquals(passedPostInfo.getDescription(), post.getDescription()),
                () -> assertEquals(passedPostInfo.getContent(), post.getContent()),
                () -> assertEquals(passedPostInfo.getCreatedTime(), post.getCreatedTime())
        );
        verify(service).update(testPostInfo.getId(), testPostInfo);
    }

    @Test
    void deleteShouldInvokeDeleteOnService() {

        final ResponseEntity<?> response = controller.deletePost(testPostInfo.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(service).delete(testPostInfo.getId());
    }
}
