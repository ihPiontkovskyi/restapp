package com.epam.training.restapp.service.impl;

import com.epam.training.restapp.dto.PostInfo;
import com.epam.training.restapp.entity.Post;
import com.epam.training.restapp.exception.ElementNotFoundException;
import com.epam.training.restapp.repository.PostRepository;
import com.epam.training.restapp.service.PostService;
import com.epam.training.restapp.service.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    @Override
    public Page<PostInfo> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::mapToDto);
    }

    @Override
    public PostInfo create(PostInfo dto) {
        return mapper.mapToDto(repository.save(mapper.mapToEntity(dto)));
    }

    @Override
    public PostInfo update(Integer id, PostInfo dto) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .map(e -> {
                    e.setTitle(dto.getTitle());
                    e.setModifiedTime(dto.getModifiedTime());
                    e.setDescription(dto.getDescription());
                    e.setCreatedTime(dto.getCreatedTime());
                    e.setContent(dto.getContent());
                    return mapper.mapToDto(repository.save(mapper.mapToEntity(e)));
                }).orElseThrow(() -> new ElementNotFoundException("Post id : " + id + " not found!"));
    }

    @Override
    public void delete(Integer id) {
        final Optional<Post> post = repository.findById(id);
        if (!post.isPresent()) {
            throw new ElementNotFoundException("Post id : " + id + " not found!");
        }
        post.ifPresent(repository::delete);
    }


}
