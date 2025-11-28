package com.ingesoft.redsocial.servicios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@SpringBootTest
@Transactional
class Cu02IniciarSesionTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarios;

    @Test
    void iniciarSesionConExito() throws Exception {
        usuarios.deleteAll();

        Usuario u = new Usuario();
        u.setLogin("juan");
        u.setNombre("Juan");
        u.setPassword("secreto");
        usuarios.save(u);

        assertDoesNotThrow(() -> usuarioService.iniciarSesion("juan", "secreto"));
    }

    @Test
    void iniciarSesionFallaSiUsuarioNoExiste() {
        usuarios.deleteAll();

        assertThrows(Exception.class, () -> usuarioService.iniciarSesion("noexiste", "pwd"));
    }

    @Test
    void iniciarSesionFallaSiPasswordNoCoincide() throws Exception {
        usuarios.deleteAll();

        Usuario u = new Usuario();
        u.setLogin("ana");
        u.setNombre("Ana");
        u.setPassword("correcto");
        usuarios.save(u);

        assertThrows(Exception.class, () -> usuarioService.iniciarSesion("ana", "incorrecto"));
    }

}