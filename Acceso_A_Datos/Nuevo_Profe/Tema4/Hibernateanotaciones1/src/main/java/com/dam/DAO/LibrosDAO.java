package com.dam.DAO;

import com.dam.model.Autores;
import com.dam.model.Libros;
import java.util.List;

public interface LibrosDAO {
    List<Libros> obtenerLibros();

    List<Autores> obtenerAutores();
}