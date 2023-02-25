package ru.clevertec.ilkevich.receipt.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AbstractService<T, L> {

    List<T> getAll();

    T findById(L value);

    void save(T model);

    T update(T model);

    void deleteById(L value);
}
