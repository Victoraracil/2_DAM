package com.dam.gym;

import java.util.*;
public class RepositorioSocios {
    private final Map<String, Socio> datos = new HashMap<>();
    public void guardar(Socio s){
        datos.put(s.getId(), s);
    }
    public Socio buscarPorEmail(String email){
        return datos.values().stream()
                .filter(x -> x.getEmail().equals(email))
                .findFirst().orElse(null);
    }
    public List<Socio> listar(){
        return new ArrayList<>(datos.values());
    }
}

