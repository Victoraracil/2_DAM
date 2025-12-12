package com.dam.repository;

import com.dam.model.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskRepository {

    Task save(Task task);

    List<Task> findAll();

    Optional<Task> findById(Long id);

    void deleteById(Long id);

    Task update(Task task);
}
