package com.dam.gym;

import java.util.UUID;
public class ServicioSocios {
    private final RepositorioSocios repo;
    public ServicioSocios(RepositorioSocios repo){ this.repo = repo; }

    public Socio registrar(String nombre, String email){
        if(nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Nombre obligatorio");
        if(email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email no válido");
        if(repo.buscarPorEmail(email) != null)
            throw new IllegalArgumentException("Ya existe un socio con ese email");
        String id = UUID.randomUUID().toString();
        Socio s = new Socio(id, nombre, email);
        repo.guardar(s);
        return s;
    }

    public java.util.List<Socio> listar(){ return repo.listar(); }
}
