package com.example.dao;

import java.util.List;

public interface IDao<T> {
    boolean create(T e);
    boolean update(T e);
    boolean delete(T e);
    T findById(int id);
    List<T> findAll();
}

