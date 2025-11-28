package com.ingesoft.redsocial.seguridad;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.ingesoft.redsocial.modelo.Usuario;

@Component
@SessionScope
public class SesionUsuario {

    private Usuario usuarioActual;

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public boolean estaAutenticado() {
        return usuarioActual != null;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public String getLoginActual() {
        return usuarioActual != null ? usuarioActual.getLogin() : null;
    }

}
