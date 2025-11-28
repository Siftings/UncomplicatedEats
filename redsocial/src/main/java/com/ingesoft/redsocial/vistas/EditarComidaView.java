package com.ingesoft.redsocial.vistas;

import com.ingesoft.redsocial.modelo.AlimentoComida;
import com.ingesoft.redsocial.modelo.Comida;
import com.ingesoft.redsocial.seguridad.SesionUsuario;
import com.ingesoft.redsocial.servicios.ComidaService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route("editar-comida")
public class EditarComidaView extends VerticalLayout 
        implements BeforeEnterObserver, HasUrlParameter<Long> {

    private final ComidaService comidaService;
    private final SesionUsuario sesionUsuario;

    private Long comidaId;
    private Comida comida;

    private H3 infoComida;
    private Grid<AlimentoComida> gridAlimentos;

    public EditarComidaView(ComidaService comidaService, SesionUsuario sesionUsuario) {
        this.comidaService = comidaService;
        this.sesionUsuario = sesionUsuario;

        setSizeFull();
        setPadding(true);
        setSpacing(true);
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        this.comidaId = parameter;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!sesionUsuario.estaAutenticado()) {
            event.forwardTo(LoginView.class);
            return;
        }

        if (comidaId != null) {
            try {
                comida = comidaService.obtenerComidaPorId(comidaId);
                crearInterfaz();
                cargarDatos();
            } catch (Exception e) {
                mostrarError("Error al cargar la comida");
                event.forwardTo(ResumenDiarioView.class);
            }
        }
    }

    private void crearInterfaz() {
        // Header
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);

        H2 titulo = new H2("✏️ Editar Comida");

        Button volverBtn = new Button("← Volver", event -> volver());
        volverBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        header.add(titulo, volverBtn);

        // Info de la comida
        infoComida = new H3();

        // Grid de alimentos
        gridAlimentos = new Grid<>(AlimentoComida.class, false);
        gridAlimentos.addColumn(ac -> ac.getAlimento().getNombre()).setHeader("Alimento");
        gridAlimentos.addColumn(AlimentoComida::getCantidadGramos).setHeader("Cantidad (g)");
        gridAlimentos.addColumn(AlimentoComida::getCaloriasCalculadas).setHeader("Calorías");
        gridAlimentos.addColumn(AlimentoComida::getProteinasCalculadas).setHeader("Proteínas");
        gridAlimentos.addColumn(AlimentoComida::getCarbohidratosCalculados).setHeader("Carbos");
        gridAlimentos.addColumn(AlimentoComida::getGrasasCalculadas).setHeader("Grasas");

        gridAlimentos.addComponentColumn(alimentoComida -> {
            HorizontalLayout botones = new HorizontalLayout();

            Button editarBtn = new Button("✏️", event -> editarCantidad(alimentoComida));
            editarBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);

            Button eliminarBtn = new Button("🗑️", event -> eliminarAlimento(alimentoComida.getId()));
            eliminarBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);

            botones.add(editarBtn, eliminarBtn);
            return botones;
        }).setHeader("Acciones");

        gridAlimentos.setHeight("400px");

        add(header, infoComida, gridAlimentos);
    }

    private void cargarDatos() {
        infoComida.setText(String.format("%s - %s - %d kcal",
            comida.getNombre(),
            comida.getCategoria().getNombre(),
            comida.getTotalCalorias()));

        gridAlimentos.setItems(comida.getAlimentos());
    }

    private void editarCantidad(AlimentoComida alimentoComida) {
        // Crear diálogo simple para editar cantidad
        VerticalLayout dialogContent = new VerticalLayout();
        
        Span mensaje = new Span("Modificar cantidad de: " + alimentoComida.getAlimento().getNombre());
        
        IntegerField cantidadField = new IntegerField("Nueva cantidad (gramos)");
        cantidadField.setValue(alimentoComida.getCantidadGramos());
        cantidadField.setMin(1);
        cantidadField.setMax(10000);

        Button guardarBtn = new Button("Guardar", event -> {
            try {
                comidaService.modificarCantidadAlimento(
                    alimentoComida.getId(),
                    cantidadField.getValue()
                );
                comida = comidaService.obtenerComidaPorId(comidaId);
                cargarDatos();
                mostrarExito("Cantidad modificada");
            } catch (Exception e) {
                mostrarError(e.getMessage());
            }
        });
        guardarBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        dialogContent.add(mensaje, cantidadField, guardarBtn);

        // Por simplicidad, se agrega como componente temporal
        // En una implementación real, se usaría un Dialog de Vaadin
        add(dialogContent);
    }

    private void eliminarAlimento(Long alimentoComidaId) {
        try {
            comidaService.eliminarAlimentoDeComida(alimentoComidaId);
            comida = comidaService.obtenerComidaPorId(comidaId);
            cargarDatos();
            mostrarExito("Alimento eliminado");
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void volver() {
        UI.getCurrent().navigate(ResumenDiarioView.class);
    }

    private void mostrarError(String mensaje) {
        Notification.show(mensaje, 3000, Notification.Position.TOP_CENTER)
            .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void mostrarExito(String mensaje) {
        Notification.show(mensaje, 2000, Notification.Position.TOP_CENTER)
            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

}
