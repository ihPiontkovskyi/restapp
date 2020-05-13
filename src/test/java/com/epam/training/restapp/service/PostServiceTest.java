package com.epam.training.restapp.service;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.entity.Post;
import com.epam.training.restapp.entity.User;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

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
    private PostInfo passedPostInfoData;
    private Post testPost;
    private Post passedPostData;

    @BeforeEach
    void intiData() {
        testPostInfo = PostInfo.builder()
                .content("Some text")
                .createdTime(LocalDateTime.of(2019, 5, 13, 14, 0, 0))
                .description("Some description")
                .id(1)
                .modifiedTime(LocalDateTime.of(2020, 5, 13, 14, 0, 0))
                .title("Some title")
                .userId(1)
                .build();
        passedPostInfoData = PostInfo.builder()
                .content("text")
                .createdTime(LocalDateTime.of(2018, 5, 13, 14, 0, 0))
                .description("description")
                .id(2)
                .modifiedTime(LocalDateTime.of(2019, 5, 12, 14, 0, 0))
                .title("title")
                .userId(2)
                .build();
        testPost = Post.builder()
                .content("Some text")
                .createdTime(LocalDateTime.of(2019, 5, 13, 14, 0, 0))
                .description("Some description")
                .id(1)
                .modifiedTime(LocalDateTime.of(2020, 5, 13, 14, 0, 0))
                .title("Some title")
                .user(User.builder().id(1).build())
                .build();
        passedPostData = Post.builder()
                .content("text")
                .createdTime(LocalDateTime.of(2018, 5, 13, 14, 0, 0))
                .description("description")
                .id(2)
                .modifiedTime(LocalDateTime.of(2019, 5, 12, 14, 0, 0))
                .title("title")
                .user(User.builder().id(2).build())
                .build();
    }

    @AfterEach
    void resetData() {
        reset(repository, mapper);
    }

    @Test
    void createShouldReturnTestPostInfoData() {
        when(mapper.mapToEntity(testPostInfo)).thenReturn(testPost);
        when(repository.save(testPost)).thenReturn(passedPostData);
        when(mapper.mapToDto(passedPostData)).thenReturn(passedPostInfoData);

        PostInfo result = service.create(testPostInfo);

        assertAll(
                () -> assertEquals(passedPostInfoData.getUserId(), result.getUserId()),
                () -> assertEquals(passedPostInfoData.getTitle(), result.getTitle()),
                () -> assertEquals(passedPostInfoData.getId(), result.getId()),
                () -> assertEquals(passedPostInfoData.getModifiedTime(), result.getModifiedTime()),
                () -> assertEquals(passedPostInfoData.getDescription(), result.getDescription()),
                () -> assertEquals(passedPostInfoData.getContent(), result.getContent()),
                () -> assertEquals(passedPostInfoData.getCreatedTime(), result.getCreatedTime())
        );
        verify(mapper).mapToEntity(testPostInfo);
        verify(repository).save(testPost);
        verify(mapper).mapToDto(passedPostData);
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
        final PageImpl<Post> page = new PageImpl<>(Collections.singletonList(passedPostData), pageable, 1);
        when(repository.findAll(pageable)).thenReturn(page);
        when(mapper.mapToDto(passedPostData)).thenReturn(passedPostInfoData);

        Page<PostInfo> result = service.getAll(pageable);

        assertEquals(1, result.getSize());
        verify(repository).findAll(pageable);
        verify(mapper).mapToDto(passedPostData);
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
        when(mapper.mapToDto(testPost)).thenReturn(passedPostInfoData);
        when(mapper.mapToEntity(passedPostInfoData)).thenReturn(passedPostData);
        when(repository.save(passedPostData)).thenReturn(passedPostData);
        PostInfo emptyPostInfo = PostInfo.builder().build();
        when(mapper.mapToDto(passedPostData)).thenReturn(emptyPostInfo);

        PostInfo result = service.update(1, testPostInfo);

        assertAll(
                () -> assertNull(result.getCreatedTime()),
                () -> assertNull(result.getContent()),
                () -> assertNull(result.getDescription()),
                () -> assertNull(result.getModifiedTime()),
                () -> assertNull(result.getId()),
                () -> assertNull(result.getTitle()),
                () -> assertNull(result.getUserId())
        );
        verify(repository).findById(1);
        verify(mapper).mapToDto(testPost);
        verify(mapper).mapToEntity(passedPostInfoData);
        verify(repository).save(passedPostData);
        verify(mapper).mapToDto(passedPostData);
    }
}
