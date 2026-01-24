package com.dam.DAO;

import com.dam.model.Autores;
import com.dam.model.Libros;
import com.dam.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class LibrosDAOImpl implements LibrosDAO {
    @Override
    public List<Libros> obtenerLibros() {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            // HQL: Usamos el nombre de la Clase, no de la tabla
            Query<Libros> consulta = session.createQuery("from Libros", Libros.class);
            return consulta.list();
        } catch (Exception e) {
            System.err.println("Error al listar libros: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Autores> obtenerAutores() {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            // HQL: Usamos el nombre de la Clase, no de la tabla
            Query<Autores> consulta = session.createQuery("from Autores", Autores.class);
            return consulta.list();
        } catch (Exception e) {
            System.err.println("Error al listar autores: " + e.getMessage());
            return null;
        }
    }
}