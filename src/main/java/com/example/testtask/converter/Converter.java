package com.example.testtask.converter;

import java.util.List;

public abstract class Converter<E, D> {
    abstract E toEntity(D dto);

    abstract D toDto(E entity);

    public List<E> toEntityList(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).toList();
    }

    public List<D> toDtoList(List<E> entityList) {
        return entityList.stream().map(this::toDto).toList();
    }
}
