package com.ingesoft.redsocial.vistas;

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

@Route("registro")
@AnonymousAllowed
public class RegistroView extends VerticalLayout {

    private final UsuarioService usuarioService;

    private TextField loginField;
    private TextField nombreField;
    private PasswordField passwordField;
    private PasswordField confirmarPasswordField;

    public RegistroView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        crearFormularioRegistro();
    }

    private void crearFormularioRegistro() {
        VerticalLayout registroForm = new VerticalLayout();
        registroForm.setWidth("400px");
        registroForm.setPadding(true);
        registroForm.getStyle().set("border", "1px solid #ccc")
                               .set("border-radius", "8px")
                               .set("box-shadow", "0 2px 10px rgba(0,0,0,0.1)");

        H1 titulo = new H1("🍎 FitTracker");
        titulo.getStyle().set("text-align", "center")
                         .set("color", "#4CAF50");

        H2 subtitulo = new H2("Registro de Usuario");
        subtitulo.getStyle().set("text-align", "center")
                            .set("margin-top", "0");

        loginField = new TextField("Usuario");
        loginField.setWidth("100%");
        loginField.setPlaceholder("Elija un nombre de usuario");

        nombreField = new TextField("Nombre Completo");
        nombreField.setWidth("100%");
        nombreField.setPlaceholder("Ingrese su nombre");

        passwordField = new PasswordField("Contraseña");
        passwordField.setWidth("100%");
        passwordField.setPlaceholder("Mínimo 6 caracteres");

        confirmarPasswordField = new PasswordField("Confirmar Contraseña");
        confirmarPasswordField.setWidth("100%");
        confirmarPasswordField.setPlaceholder("Repita su contraseña");

        Button registrarButton = new Button("Registrarse", event -> registrar());
        registrarButton.setWidth("100%");
        registrarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button volverButton = new Button("Ya tengo cuenta", event -> volverALogin());
        volverButton.setWidth("100%");

        registroForm.add(titulo, subtitulo, loginField, nombreField, 
                        passwordField, confirmarPasswordField, registrarButton, volverButton);
        add(registroForm);
    }

    private void registrar() {
        String login = loginField.getValue();
        String nombre = nombreField.getValue();
        String password = passwordField.getValue();
        String confirmar = confirmarPasswordField.getValue();

        if (login.isEmpty() || nombre.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }

        if (!password.equals(confirmar)) {
            mostrarError("Las contraseñas no coinciden");
            return;
        }

        try {
            usuarioService.registrarNuevoUsuario(login, nombre, password);
            
            Notification.show("¡Registro exitoso! Ya puede iniciar sesión", 3000, 
                             Notification.Position.TOP_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            
            UI.getCurrent().navigate(LoginView.class);
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void volverALogin() {
        UI.getCurrent().navigate(LoginView.class);
    }

    private void mostrarError(String mensaje) {
        Notification.show(mensaje, 3000, Notification.Position.TOP_CENTER)
            .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

}
