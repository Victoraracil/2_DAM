package com.dam.controller;

import com.dam.model.Task;
import com.dam.service.TaskService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public void registerRoutes(Javalin app) {

        app.get("/api/tasks", this::getAllTasks);

        app.post("/api/tasks", this::createTask);

        app.put("/api/tasks/{id}", this::markAsCompleted);

        app.delete("/api/tasks/{id}", this::deleteTask);
    }

    private void getAllTasks(Context ctx) {
        ctx.json(taskService.getAllTasks());
    }

    private void createTask(Context ctx) {
        Task task = ctx.bodyValidator(Task.class)
                .check(t -> t.getTitle() != null && !t.getTitle().isBlank(), "El título es obligatorio")
                .get();

        Task created = taskService.createTask(task);
        ctx.status(201).json(created);
    }

    private void markAsCompleted(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Task updated = taskService.markTaskAsCompleted(id);
        ctx.json(updated);
    }

    private void deleteTask(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        taskService.deleteTask(id);
        ctx.status(204);
    }
}

