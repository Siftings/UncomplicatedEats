package com.ingesoft.redsocial.vistas;

import com.ingesoft.redsocial.seguridad.SesionUsuario;
import com.ingesoft.redsocial.servicios.UsuarioService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    private final UsuarioService usuarioService;
    private final SesionUsuario sesionUsuario;

    private TextField loginField;
    private PasswordField passwordField;

    public LoginView(UsuarioService usuarioService, SesionUsuario sesionUsuario) {
        this.usuarioService = usuarioService;
        this.sesionUsuario = sesionUsuario;

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        crearFormularioLogin();
    }

    private void crearFormularioLogin() {
        VerticalLayout loginForm = new VerticalLayout();
        loginForm.setWidth("400px");
        loginForm.setPadding(true);
        loginForm.getStyle().set("border", "1px solid #ccc")
                             .set("border-radius", "8px")
                             .set("box-shadow", "0 2px 10px rgba(0,0,0,0.1)");

        H1 titulo = new H1("🍎 FitTracker");
        titulo.getStyle().set("text-align", "center")
                         .set("color", "#4CAF50");

        H2 subtitulo = new H2("Iniciar Sesión");
        subtitulo.getStyle().set("text-align", "center")
                            .set("margin-top", "0");

        loginField = new TextField("Usuario");
        loginField.setWidth("100%");
        loginField.setPlaceholder("Ingrese su login");

        passwordField = new PasswordField("Contraseña");
        passwordField.setWidth("100%");
        passwordField.setPlaceholder("Ingrese su contraseña");

        Button loginButton = new Button("Ingresar", event -> iniciarSesion());
        loginButton.setWidth("100%");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button registroButton = new Button("Registrarse", event -> irARegistro());
        registroButton.setWidth("100%");

        loginForm.add(titulo, subtitulo, loginField, passwordField, loginButton, registroButton);
        add(loginForm);
    }

    private void iniciarSesion() {
        String login = loginField.getValue();
        String password = passwordField.getValue();

        if (login.isEmpty() || password.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }

        try {
            var usuario = usuarioService.iniciarSesion(login, password);
            sesionUsuario.setUsuarioActual(usuario);
            
            Notification.show("¡Bienvenido " + usuario.getNombre() + "!", 3000, Notification.Position.TOP_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            
            UI.getCurrent().navigate(ResumenDiarioView.class);
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void irARegistro() {
        UI.getCurrent().navigate(RegistroView.class);
    }

    private void mostrarError(String mensaje) {
        Notification.show(mensaje, 3000, Notification.Position.TOP_CENTER)
            .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

}
