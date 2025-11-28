package com.ingesoft.redsocial.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarios;

    // CU01 - Registrar nuevo usuario
    public Usuario registrarNuevoUsuario(
        String login,
        String nombre,
        String password
    ) throws Exception {

        // 2. Sistema verifica que no exista otro usuario con ese login
        if (usuarios.existsById(login)) {
            throw new Exception("Ya existe otro usuario con ese login");
        }

        // 5. Sistema valida que el password tenga más de 5 letras
        if (password == null || password.length() <= 5) {
            throw new Exception("La contraseña no cumple con la política de la red social");
        }

        // 6. Sistema crea el nuevo usuario 
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setNombre(nombre);
        usuario.setPassword(password);
        usuario.setObjetivoCalorias(2000); // Valor por defecto
        
        return usuarios.save(usuario);
    }

    // CU02 - Iniciar sesión
    public Usuario iniciarSesion(
        String login,
        String password
    ) throws Exception {

        // 2. Sistema verifica que exista un usuario con ese login
        if (!usuarios.existsById(login)) {
            throw new Exception("No existe un usuario con ese login");
        }

        // 4. Sistema verifica que el password coincida con el password de ese usuario
        Usuario usuario = usuarios.findById(login).get();
        
        if (!password.equals(usuario.getPassword())) {
            throw new Exception("No coincide el password");
        }

        // 5. Sistema establece el usuario actual
        return usuario;
    }

    public Usuario obtenerUsuario(String login) throws Exception {
        return usuarios.findById(login)
            .orElseThrow(() -> new Exception("No existe un usuario con ese login"));
    }

    public Usuario actualizarObjetivoCalorias(String login, Integer objetivo) throws Exception {
        Usuario usuario = obtenerUsuario(login);
        usuario.setObjetivoCalorias(objetivo);
        return usuarios.save(usuario);
    }

}
