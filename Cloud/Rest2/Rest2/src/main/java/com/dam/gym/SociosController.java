package com.dam.gym;

import io.javalin.http.Context;
public class SociosController {
    private final ServicioSocios servicio;
    public SociosController(ServicioSocios servicio){ this.servicio = servicio; }

    public void crear(Context ctx){
        try {
            SocioCrear body = ctx.bodyAsClass(SocioCrear.class);
            Socio creado = servicio.registrar(body.nombre, body.email);
            ctx.status(201).json(creado);
        } catch (IllegalArgumentException e){
            ctx.status(400).json(java.util.Map.of("error", e.getMessage()));
        }
    }

    public void listar(Context ctx){
        ctx.json(servicio.listar());
    }
}
