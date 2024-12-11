package com.example.demo;

public interface IGeneric<T> {
    void add();
    void remove();
    T update(T entity);
}
