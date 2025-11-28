package com.ingesoft.redsocial.servicios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class Cu02IniciarSesionMockTest {

    @Mock
    UsuarioRepository usuarios;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void iniciarSesionConExito() throws Exception {
        String login = "juan";
        String password = "secreto";

        Usuario u = new Usuario();
        u.setLogin(login);
        u.setPassword(password);

        when(usuarios.existsById(login)).thenReturn(true);
        when(usuarios.findById(login)).thenReturn(Optional.of(u));

        assertDoesNotThrow(() -> usuarioService.iniciarSesion(login, password));
    }

    @Test
    void iniciarSesionFallaSiUsuarioNoExiste() {
        String login = "noexiste";
        String password = "any";

        when(usuarios.existsById(login)).thenReturn(false);

        assertThrows(Exception.class, () -> usuarioService.iniciarSesion(login, password));
    }

    @Test
    void iniciarSesionFallaSiPasswordNoCoincide() {
        String login = "ana";
        String actualPwd = "correcto";
        String intento = "incorrecto";

        Usuario u = new Usuario();
        u.setLogin(login);
        u.setPassword(actualPwd);

        when(usuarios.existsById(login)).thenReturn(true);
        when(usuarios.findById(login)).thenReturn(Optional.of(u));

        assertThrows(Exception.class, () -> usuarioService.iniciarSesion(login, intento));
    }

}
