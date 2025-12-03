package com.dam.gym;

import io.javalin.http.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class SociosControllerTest {
    @Mock
    ServicioSocios servicioMock;
    @Mock
    Context ctxMock;
    @InjectMocks
    SociosController controller;

    @Test
    void crear_debe_retornar_201_y_json(){
        SocioCrear body = new SocioCrear();
        body.nombre = "Laura";
        body.email = "laura@gym.com";
        when(ctxMock.bodyAsClass(SocioCrear.class)).thenReturn(body);

        Socio socioCreado = new Socio("id-123","Laura","laura@gym.com");
        when(servicioMock.registrar("Laura","laura@gym.com")).thenReturn(socioCreado);

        when(ctxMock.status(201)).thenReturn(ctxMock);

        controller.crear(ctxMock);

        verify(servicioMock).registrar("Laura","laura@gym.com");
        verify(ctxMock).status(201);
        verify(ctxMock).json(socioCreado);
    }
}
