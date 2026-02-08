package com.accesoadatos.biblioteca_api.controllers;

import com.accesoadatos.biblioteca_api.model.Usuario;
import com.accesoadatos.biblioteca_api.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping("/usuarios")
    public List<Usuario> verUsuarios() {
        return usuarioRepository.findAll();
    }
}
