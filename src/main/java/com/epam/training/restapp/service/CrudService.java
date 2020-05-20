package com.epam.training.restapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<D> {
    Page<D> getAll(Pageable pageable);

    D create(D dto);

    D update(Integer id, D dto);

    void delete(Integer id);
}
