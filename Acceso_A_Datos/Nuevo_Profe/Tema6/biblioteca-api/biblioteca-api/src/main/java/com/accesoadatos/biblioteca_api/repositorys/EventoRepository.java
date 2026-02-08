package com.accesoadatos.biblioteca_api.repositorys;

import com.accesoadatos.biblioteca_api.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByPrecioGreaterThan(double precio);
}
