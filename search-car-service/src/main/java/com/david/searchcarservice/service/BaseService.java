package com.david.searchcarservice.service;

import java.util.List;

public interface BaseService <T, ID> {

    T findById(ID id);

    List<T> findAll();

    T update(Long id, T entity);

    void deleteById(ID id);

    boolean isExist(ID id);
}