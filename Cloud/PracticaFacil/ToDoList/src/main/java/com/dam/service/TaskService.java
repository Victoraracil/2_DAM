package com.dam.service;

import com.dam.model.Task;
import com.dam.repository.ITaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskService {

    // La magia del desacoplamiento: usamos la INTERFAZ
    private final ITaskRepository repository;

    public TaskService(ITaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task createTask(Task task) {
        // Validaciones de negocio
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        task.setCompleted(false); // siempre empieza sin completar
        return repository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return repository.findById(id);
    }

    public Task updateTask(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("La tarea debe tener ID para actualizar");
        }
        return repository.update(task);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Task markTaskAsCompleted(Long id) {
        Optional<Task> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Tarea no encontrada");
        }

        Task task = optional.get();
        task.setCompleted(true);
        return repository.update(task);
    }
}

