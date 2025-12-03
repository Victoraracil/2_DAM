package com.dam;

import com.dam.gym.RepositorioSocios;
import com.dam.gym.ServicioSocios;
import com.dam.gym.SociosController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        //Crear las capas
        RepositorioSocios repo = new RepositorioSocios();
        ServicioSocios servicio = new ServicioSocios(repo);
        SociosController controller = new SociosController(servicio);

        //Crear la app Javalin
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        });

        //Registrar las rutas
        app.post("/socios", controller::crear);
        app.get("/socios", controller::listar);

        //Iniciar servidor
        app.start(7070);

        System.out.println("Servidor iniciado en http://localhost:7070");
    }
}
