package com.epam.training.restapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<D> {
    Page<D> getAllPosts(Pageable pageable);

    D createPost(D dto);

    D updatePost(Integer id, D dto);

    void delete(Integer id);
}
