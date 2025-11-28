package com.ingesoft.redsocial.servicios;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class Cu01RegistrarNuevoUsuarioMockTest {

    @Mock
    UsuarioRepository usuarios;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void registraUsuarioConExito() throws Exception {
        String login = "juan";
        String nombre = "Juan Perez";
        String password = "abcdefg";

        when(usuarios.existsById(login)).thenReturn(false);
        when(usuarios.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> usuarioService.registrarNuevoUsuario(login, nombre, password));

        verify(usuarios).save(argThat(u ->
            login.equals(u.getLogin()) && nombre.equals(u.getNombre()) && password.equals(u.getPassword())
        ));
    }

    @Test
    void fallaSiLoginYaExiste() throws Exception {
        String login = "maria";
        String nombre = "Maria";
        String password = "secure123";

        when(usuarios.existsById(login)).thenReturn(true);

        assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, password)
        );
    }

    @Test
    void fallaSiPasswordEsNuloOInvalido() throws Exception {
        String login = "pedro";
        String nombre = "Pedro";

        // password nulo
        when(usuarios.existsById(login)).thenReturn(false);
        assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, null)
        );

        // password demasiado corto
        assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, "12345")
        );
    }

}
