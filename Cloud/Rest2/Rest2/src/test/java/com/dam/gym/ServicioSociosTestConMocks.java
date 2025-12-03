package com.dam.gym;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ServicioSociosTestConMocks {
    @Mock
    RepositorioSocios repoMock;
    @InjectMocks
    ServicioSocios servicio;

    @Test
    void registrar_debe_fallar_si_email_existe_mock(){
        Socio existente = new Socio("id1","Old","repe@gym.com");
        when(repoMock.buscarPorEmail("repe@gym.com")).thenReturn(existente);

        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar("New", "repe@gym.com"));

        verify(repoMock, never()).guardar(any());
    }
}
