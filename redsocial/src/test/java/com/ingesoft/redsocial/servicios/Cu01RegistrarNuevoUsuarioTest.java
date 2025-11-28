package com.ingesoft.redsocial.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@SpringBootTest
@Transactional
class Cu01RegistrarNuevoUsuarioTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarios;

    @Test
    void registraUsuarioEnLaBaseDeDatos() throws Exception {

    // Arrange -- prepara la prueba

        // Aseguramos estado inicial limpio
        usuarios.deleteAll();

    // Act -- realiza la acción
        String login = "integ_user";
        String nombre = "Integracion Usuario";
        String password = "password123";

        // Ejecutar caso de uso real
        usuarioService.registrarNuevoUsuario(login, nombre, password);

    // Assert -- revisa resultado y  estado final del sistema

        // Verificar que quedó persistido
        assertTrue(usuarios.existsById(login));
        Usuario u = usuarios.findById(login).orElseThrow();
        assertEquals(nombre, u.getNombre());
        assertEquals(password, u.getPassword());
    }

    @Test
    void fallaSiLoginYaExisteEnBD() throws Exception {
    
    // Arrange -- prepara la prueba

        usuarios.deleteAll();

        String login = "existing_user";
        String nombre = "Existente";
        String password = "password987";

        // Insertar usuario preexistente en la BD
        Usuario previo = new Usuario();
        previo.setLogin(login);
        previo.setNombre("Previo");
        previo.setPassword("xpto12345");
        usuarios.save(previo);


    // Act -- ejecuta la acción
    // Assert -- revisa el resultado

        // Intentar registrar con el mismo login debe lanzar excepción
        assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, password)
        );
    }

    @Test
    void fallaSiPasswordInvalidoEnBD() throws Exception {

        String login = "bd_short_pwd";
        String nombre = "Pwd Corta";

        // Arrange -- prepara la prueba

        usuarios.deleteAll();

        // Act-Assert -- realiza la acción
    
        // password nulo
        assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, null)
        );

        // password demasiado corto
        assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, "12345")
        );
    }

}
