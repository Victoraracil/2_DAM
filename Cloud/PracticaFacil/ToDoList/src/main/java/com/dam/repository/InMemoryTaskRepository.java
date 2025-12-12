package com.dam.repository;


import com.dam.model.Task;

import java.util.*;

public class InMemoryTaskRepository implements ITaskRepository {

    // "Base de datos" en memoria
    private final Map<Long, Task> storage = new HashMap<>();

    // Generador de IDs autoincremental
    private Long currentId = 1L;

    @Override
    public Task save(Task task) {
        task.setId(currentId);
        storage.put(currentId, task);
        currentId++;
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public Task update(Task task) {
        storage.put(task.getId(), task);
        return task;
    }
}
