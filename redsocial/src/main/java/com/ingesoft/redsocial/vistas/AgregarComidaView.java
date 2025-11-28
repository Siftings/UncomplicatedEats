package com.ingesoft.redsocial.vistas;

import java.util.List;

import com.ingesoft.redsocial.modelo.Alimento;
import com.ingesoft.redsocial.modelo.CategoriaComida;
import com.ingesoft.redsocial.modelo.Comida;
import com.ingesoft.redsocial.seguridad.SesionUsuario;
import com.ingesoft.redsocial.servicios.AlimentoService;
import com.ingesoft.redsocial.servicios.CategoriaComidaService;
import com.ingesoft.redsocial.servicios.ComidaService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("agregar-comida")
public class AgregarComidaView extends VerticalLayout implements BeforeEnterObserver {

    private final ComidaService comidaService;
    private final CategoriaComidaService categoriaService;
    private final AlimentoService alimentoService;
    private final SesionUsuario sesionUsuario;

    private Comida comidaActual;

    private ComboBox<CategoriaComida> categoriaComboBox;
    private TextField nombreField;
    private TextField busquedaAlimentoField;
    private Grid<Alimento> gridAlimentos;
    private IntegerField cantidadField;
    private Button agregarAlimentoBtn;

    public AgregarComidaView(ComidaService comidaService, CategoriaComidaService categoriaService,
                            AlimentoService alimentoService, SesionUsuario sesionUsuario) {
        this.comidaService = comidaService;
        this.categoriaService = categoriaService;
        this.alimentoService = alimentoService;
        this.sesionUsuario = sesionUsuario;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        crearInterfaz();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!sesionUsuario.estaAutenticado()) {
            event.forwardTo(LoginView.class);
        }
    }

    private void crearInterfaz() {
        // Header
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);

        H2 titulo = new H2("➕ Agregar Nueva Comida");

        Button volverBtn = new Button("← Volver", event -> volver());
        volverBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        header.add(titulo, volverBtn);

        // Paso 1: Seleccionar categoría
        categoriaComboBox = new ComboBox<>("Categoría");
        categoriaComboBox.setWidth("300px");
        categoriaComboBox.setItemLabelGenerator(CategoriaComida::getNombre);
        categoriaComboBox.setItems(categoriaService.obtenerTodasLasCategorias());
        categoriaComboBox.setRequired(true);

        nombreField = new TextField("Nombre de la comida (opcional)");
        nombreField.setWidth("300px");
        nombreField.setPlaceholder("Ej: Desayuno saludable");

        Button crearComidaBtn = new Button("Crear Comida", event -> crearComida());
        crearComidaBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Paso 2: Buscar y agregar alimentos (se muestra después de crear la comida)
        busquedaAlimentoField = new TextField("Buscar alimento");
        busquedaAlimentoField.setWidth("100%");
        busquedaAlimentoField.setPlaceholder("Busque por nombre del alimento...");
        busquedaAlimentoField.addValueChangeListener(event -> buscarAlimentos());
        busquedaAlimentoField.setVisible(false);

        gridAlimentos = new Grid<>(Alimento.class, false);
        gridAlimentos.addColumn(Alimento::getNombre).setHeader("Alimento").setAutoWidth(true);
        gridAlimentos.addColumn(Alimento::getCaloriasPor100g).setHeader("Calorías/100g").setAutoWidth(true);
        gridAlimentos.addColumn(Alimento::getProteinasPor100g).setHeader("Proteínas/100g").setAutoWidth(true);
        gridAlimentos.addColumn(Alimento::getCarbohidratosPor100g).setHeader("Carbos/100g").setAutoWidth(true);
        gridAlimentos.addColumn(Alimento::getGrasasPor100g).setHeader("Grasas/100g").setAutoWidth(true);
        gridAlimentos.setHeight("300px");
        gridAlimentos.asSingleSelect().addValueChangeListener(event -> {
            cantidadField.setEnabled(event.getValue() != null);
            agregarAlimentoBtn.setEnabled(event.getValue() != null);
        });
        gridAlimentos.setVisible(false);

        HorizontalLayout cantidadLayout = new HorizontalLayout();
        cantidadField = new IntegerField("Cantidad (gramos)");
        cantidadField.setMin(1);
        cantidadField.setMax(10000);
        cantidadField.setValue(100);
        cantidadField.setWidth("200px");
        cantidadField.setEnabled(false);

        agregarAlimentoBtn = new Button("Agregar Alimento", event -> agregarAlimento());
        agregarAlimentoBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        agregarAlimentoBtn.setEnabled(false);

        cantidadLayout.add(cantidadField, agregarAlimentoBtn);
        cantidadLayout.setAlignItems(Alignment.END);
        cantidadLayout.setVisible(false);

        Button terminarBtn = new Button("Terminar y Ver Resumen", event -> terminar());
        terminarBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        terminarBtn.setVisible(false);

        add(header, categoriaComboBox, nombreField, crearComidaBtn, 
            busquedaAlimentoField, gridAlimentos, cantidadLayout, terminarBtn);
    }

    private void crearComida() {
        if (categoriaComboBox.isEmpty()) {
            mostrarError("Debe seleccionar una categoría");
            return;
        }

        try {
            comidaActual = comidaService.crearComida(
                sesionUsuario.getLoginActual(),
                categoriaComboBox.getValue().getId(),
                nombreField.getValue()
            );

            // Ocultar paso 1, mostrar paso 2
            categoriaComboBox.setEnabled(false);
            nombreField.setEnabled(false);
            busquedaAlimentoField.setVisible(true);
            gridAlimentos.setVisible(true);
            getComponentAt(6).setVisible(true); // cantidadLayout
            getComponentAt(7).setVisible(true); // terminarBtn

            cargarAlimentos();

            mostrarExito("Comida creada. Ahora agregue alimentos.");
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void buscarAlimentos() {
        String busqueda = busquedaAlimentoField.getValue();
        List<Alimento> alimentos = alimentoService.buscarAlimentos(busqueda);
        gridAlimentos.setItems(alimentos);
    }

    private void cargarAlimentos() {
        gridAlimentos.setItems(alimentoService.obtenerTodosLosAlimentos());
    }

    private void agregarAlimento() {
        Alimento alimentoSeleccionado = gridAlimentos.asSingleSelect().getValue();
        Integer cantidad = cantidadField.getValue();

        if (alimentoSeleccionado == null || cantidad == null) {
            mostrarError("Seleccione un alimento e ingrese la cantidad");
            return;
        }

        try {
            comidaService.agregarAlimentoAComida(
                comidaActual.getId(),
                alimentoSeleccionado.getId(),
                cantidad
            );

            mostrarExito(String.format("Agregado: %s (%dg)", alimentoSeleccionado.getNombre(), cantidad));
            cantidadField.setValue(100);
            gridAlimentos.asSingleSelect().clear();
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void terminar() {
        UI.getCurrent().navigate(ResumenDiarioView.class);
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
