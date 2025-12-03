package com.dam.gym;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ServicioSociosTest {
    private ServicioSocios servicio;
    @BeforeEach
    void prepararEscenario(){
        RepositorioSocios repo = new RepositorioSocios();
        servicio = new ServicioSocios(repo);
    }

    @Test
    void registrar_debe_guardar_socio_cuando_datos_son_validos(){
        Socio r = servicio.registrar("Ana", "ana@gym.com");
        assertNotNull(r);
        assertEquals("Ana", r.getNombre());
        assertEquals("ana@gym.com", r.getEmail());
        assertNotNull(r.getId());
    }

    @Test
    void registrar_debe_lanzar_error_si_email_ya_existe(){
        servicio.registrar("Pepe", "pepe@gym.com");
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar("Juan", "pepe@gym.com"));
        assertEquals("Ya existe un socio con ese email", ex.getMessage());
    }

    @Test
    void registrar_debe_lanzar_error_si_nombre_vacio(){
        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar("", "valido@gym.com"));
    }

    @Test
    void registrar_debe_lanzar_error_si_email_invalido(){
        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar("Ana", "email-sin-arroba"));
    }
}
