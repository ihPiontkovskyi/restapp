package com.epam.training.restapp.service.mapper;

public interface Mapper<E, D> {
    E mapToEntity(D dto);

    D mapToDto(E entity);
}
