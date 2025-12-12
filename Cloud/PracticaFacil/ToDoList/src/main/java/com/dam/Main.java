package com.dam;

import com.dam.controller.TaskController;
import com.dam.repository.ITaskRepository;
import com.dam.repository.InMemoryTaskRepository;
import com.dam.service.TaskService;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        ITaskRepository repo = new InMemoryTaskRepository();
        TaskService service = new TaskService(repo);

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "public";
                staticFileConfig.location = io.javalin.http.staticfiles.Location.CLASSPATH;
            });

            config.http.defaultContentType = "application/json";
        }).start(8080);

        TaskController controller = new TaskController(service);
        controller.registerRoutes(app);

        System.out.println("Servidor Javalin arrancado en http://localhost:8080");
    }
}
