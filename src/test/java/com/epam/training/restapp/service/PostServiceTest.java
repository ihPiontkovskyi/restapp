package com.epam.training.restapp.service;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.entity.Post;
import com.epam.training.restapp.exception.ElementNotFoundException;
import com.epam.training.restapp.repository.PostRepository;
import com.epam.training.restapp.service.impl.PostServiceImpl;
import com.epam.training.restapp.service.mapper.PostMapper;
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

import java.util.Collections;
import java.util.Optional;

import static com.epam.training.restapp.TestDataProvider.getPostInfoPassedData;
import static com.epam.training.restapp.TestDataProvider.getPostInfoTestData;
import static com.epam.training.restapp.TestDataProvider.getPostPassedData;
import static com.epam.training.restapp.TestDataProvider.getPostTestData;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository repository;

    @Mock
    private PostMapper mapper;

    @InjectMocks
    private PostServiceImpl service;

    private PostInfo testPostInfo;
    private PostInfo passedPostInfo;
    private Post testPost;
    private Post passedPost;

    @BeforeEach
    void intiData() {
        testPostInfo = getPostInfoTestData();
        passedPostInfo = getPostInfoPassedData();
        testPost = getPostTestData();
        passedPost = getPostPassedData();
    }

    @AfterEach
    void resetData() {
        reset(repository, mapper);
    }

    @Test
    void createShouldReturnTestPostInfoData() {
        when(mapper.mapToEntity(testPostInfo)).thenReturn(testPost);
        when(repository.save(testPost)).thenReturn(passedPost);
        when(mapper.mapToDto(passedPost)).thenReturn(passedPostInfo);

        PostInfo result = service.create(testPostInfo);

        assertAll(
                () -> assertEquals(passedPostInfo.getTitle(), result.getTitle()),
                () -> assertEquals(passedPostInfo.getId(), result.getId()),
                () -> assertEquals(passedPostInfo.getModifiedTime(), result.getModifiedTime()),
                () -> assertEquals(passedPostInfo.getDescription(), result.getDescription()),
                () -> assertEquals(passedPostInfo.getContent(), result.getContent()),
                () -> assertEquals(passedPostInfo.getCreatedTime(), result.getCreatedTime())
        );
        verify(mapper).mapToEntity(testPostInfo);
        verify(repository).save(testPost);
        verify(mapper).mapToDto(passedPost);
    }

    @Test
    void findAllShouldReturnEmptyPage() {
        Pageable pageable = Mockito.mock(Pageable.class);
        when(repository.findAll(pageable)).thenReturn(Page.empty());

        Page<PostInfo> result = service.getAll(pageable);

        assertEquals(Page.empty(), result);
        verify(repository).findAll(pageable);
    }

    @Test
    void findAllShouldReturnPageWith1Element() {
        Pageable pageable = Mockito.mock(Pageable.class);
        final PageImpl<Post> page = new PageImpl<>(Collections.singletonList(passedPost), pageable, 1);
        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.mapToDto(passedPost)).thenReturn(passedPostInfo);

        Page<PostInfo> result = service.getAll(pageable);

        assertEquals(1, result.getSize());
        verify(repository).findAll(pageable);
        verify(mapper).mapToDto(passedPost);
    }

    @Test
    void deleteShouldThrowElementNotFoundException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> service.delete(1), "Post id : 1 not found!");

        verify(repository).findById(1);
    }

    @Test
    void deleteShouldDeleteElementSuccessfully() {
        when(repository.findById(1)).thenReturn(Optional.of(testPost));

        service.delete(1);

        verify(repository).findById(1);
        verify(repository).delete(testPost);
    }

    @Test
    void updateShouldThrowElementNotFoundException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> service.update(1, testPostInfo), "Post id : 1 not found!");

        verify(repository).findById(1);
    }

    @Test
    void updateShouldUpdateElementSuccessfully() {
        when(repository.findById(1)).thenReturn(Optional.of(testPost));
        when(mapper.mapToDto(testPost)).thenReturn(passedPostInfo);
        when(mapper.mapToEntity(passedPostInfo)).thenReturn(passedPost);
        when(repository.save(passedPost)).thenReturn(passedPost);
        PostInfo emptyPostInfo = PostInfo.builder().build();
        when(mapper.mapToDto(passedPost)).thenReturn(emptyPostInfo);

        PostInfo result = service.update(1, testPostInfo);

        assertAll(
                () -> assertNull(result.getCreatedTime()),
                () -> assertNull(result.getContent()),
                () -> assertNull(result.getDescription()),
                () -> assertNull(result.getModifiedTime()),
                () -> assertNull(result.getId()),
                () -> assertNull(result.getTitle())
        );
        verify(repository).findById(1);
        verify(mapper).mapToDto(testPost);
        verify(mapper).mapToEntity(passedPostInfo);
        verify(repository).save(passedPost);
        verify(mapper).mapToDto(passedPost);
    }
}
