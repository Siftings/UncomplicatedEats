package com.ingesoft.redsocial.vistas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.ingesoft.redsocial.modelo.CategoriaComida;
import com.ingesoft.redsocial.modelo.Comida;
import com.ingesoft.redsocial.seguridad.SesionUsuario;
import com.ingesoft.redsocial.servicios.ComidaService;
import com.ingesoft.redsocial.servicios.ComidaService.ResumenDiarioDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("resumen")
public class ResumenDiarioView extends VerticalLayout implements BeforeEnterObserver {

    private final ComidaService comidaService;
    private final SesionUsuario sesionUsuario;

    private DatePicker datePicker;
    private Div resumenGeneralDiv;
    private Div comidasDiv;

    public ResumenDiarioView(ComidaService comidaService, SesionUsuario sesionUsuario) {
        this.comidaService = comidaService;
        this.sesionUsuario = sesionUsuario;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        crearInterfaz();
        cargarResumen(LocalDate.now());
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
        header.setAlignItems(Alignment.CENTER);

        H1 titulo = new H1("🍎 FitTracker - Resumen Diario");
        titulo.getStyle().set("color", "#4CAF50");

        Button cerrarSesionBtn = new Button("Cerrar Sesión", event -> cerrarSesion());
        cerrarSesionBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

        header.add(titulo, cerrarSesionBtn);

        // Selector de fecha
        HorizontalLayout fechaLayout = new HorizontalLayout();
        fechaLayout.setAlignItems(Alignment.CENTER);
        
        datePicker = new DatePicker("Fecha");
        datePicker.setValue(LocalDate.now());
        datePicker.addValueChangeListener(event -> cargarResumen(event.getValue()));

        Button hoyBtn = new Button("Hoy", event -> {
            datePicker.setValue(LocalDate.now());
        });

        fechaLayout.add(datePicker, hoyBtn);

        // Botón agregar comida
        Button agregarComidaBtn = new Button("➕ Agregar Comida", event -> irAAgregarComida());
        agregarComidaBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        // Contenedor de resumen general
        resumenGeneralDiv = new Div();
        resumenGeneralDiv.getStyle()
            .set("border", "2px solid #4CAF50")
            .set("border-radius", "8px")
            .set("padding", "20px")
            .set("background", "#f9fdf9");

        // Contenedor de comidas
        comidasDiv = new Div();

        add(header, fechaLayout, agregarComidaBtn, resumenGeneralDiv, comidasDiv);
    }

    private void cargarResumen(LocalDate fecha) {
        try {
            ResumenDiarioDTO resumen = comidaService.calcularResumenDiario(
                sesionUsuario.getLoginActual(), fecha);

            mostrarResumenGeneral(resumen);
            mostrarComidas(resumen.getComidasPorCategoria());
        } catch (Exception e) {
            resumenGeneralDiv.removeAll();
            resumenGeneralDiv.add(new Span("Error al cargar resumen: " + e.getMessage()));
        }
    }

    private void mostrarResumenGeneral(ResumenDiarioDTO resumen) {
        resumenGeneralDiv.removeAll();

        H3 tituloResumen = new H3("📊 Totales del Día");
        tituloResumen.getStyle().set("margin-top", "0");

        // Calorías con barra de progreso
        Div caloriasDiv = new Div();
        Span caloriasLabel = new Span(String.format("Calorías: %d / %d kcal (%.0f%%)",
            resumen.getTotalCalorias(), 
            resumen.getObjetivoCalorias(),
            resumen.getPorcentajeObjetivo()));
        caloriasLabel.getStyle()
            .set("font-size", "18px")
            .set("font-weight", "bold");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setMin(0);
        progressBar.setMax(resumen.getObjetivoCalorias());
        progressBar.setValue(resumen.getTotalCalorias());
        progressBar.setWidth("100%");

        caloriasDiv.add(caloriasLabel, progressBar);

        // Macros
        HorizontalLayout macrosLayout = new HorizontalLayout();
        macrosLayout.setWidthFull();
        macrosLayout.setJustifyContentMode(JustifyContentMode.AROUND);

        macrosLayout.add(
            crearMacroBox("🥩 Proteínas", resumen.getTotalProteinas(), "g"),
            crearMacroBox("🍞 Carbohidratos", resumen.getTotalCarbohidratos(), "g"),
            crearMacroBox("🥑 Grasas", resumen.getTotalGrasas(), "g")
        );

        resumenGeneralDiv.add(tituloResumen, caloriasDiv, macrosLayout);
    }

    private Div crearMacroBox(String titulo, Double valor, String unidad) {
        Div box = new Div();
        box.getStyle()
            .set("text-align", "center")
            .set("padding", "10px");

        Span tituloSpan = new Span(titulo);
        tituloSpan.getStyle().set("display", "block");

        Span valorSpan = new Span(String.format("%.1f %s", valor, unidad));
        valorSpan.getStyle()
            .set("font-size", "20px")
            .set("font-weight", "bold")
            .set("display", "block");

        box.add(tituloSpan, valorSpan);
        return box;
    }

    private void mostrarComidas(Map<CategoriaComida, List<Comida>> comidasPorCategoria) {
        comidasDiv.removeAll();

        if (comidasPorCategoria.isEmpty()) {
            comidasDiv.add(new H3("No hay comidas registradas para este día"));
            return;
        }

        comidasPorCategoria.entrySet().stream()
            .sorted((e1, e2) -> e1.getKey().getOrden().compareTo(e2.getKey().getOrden()))
            .forEach(entry -> {
                CategoriaComida categoria = entry.getKey();
                List<Comida> comidas = entry.getValue();

                int totalCalorias = comidas.stream()
                    .mapToInt(Comida::getTotalCalorias)
                    .sum();

                Div categoriaDiv = new Div();
                categoriaDiv.getStyle()
                    .set("border", "1px solid #ddd")
                    .set("border-radius", "8px")
                    .set("padding", "15px")
                    .set("margin-bottom", "15px")
                    .set("background", "#fff");

                H4 categoriaTitulo = new H4(String.format("%s (%d kcal)",
                    categoria.getNombre(), totalCalorias));

                VerticalLayout comidasLayout = new VerticalLayout();
                comidasLayout.setPadding(false);
                comidasLayout.setSpacing(false);

                for (Comida comida : comidas) {
                    HorizontalLayout comidaLayout = new HorizontalLayout();
                    comidaLayout.setWidthFull();
                    comidaLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
                    comidaLayout.setAlignItems(Alignment.CENTER);

                    String hora = comida.getFechaHora().format(DateTimeFormatter.ofPattern("HH:mm"));
                    Span comidaInfo = new Span(String.format("• %s - %s - %d kcal",
                        comida.getNombre(), hora, comida.getTotalCalorias()));

                    HorizontalLayout botones = new HorizontalLayout();
                    Button editarBtn = new Button("✏️", event -> editarComida(comida.getId()));
                    editarBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
                    Button eliminarBtn = new Button("🗑️", event -> eliminarComida(comida.getId()));
                    eliminarBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);

                    botones.add(editarBtn, eliminarBtn);
                    comidaLayout.add(comidaInfo, botones);
                    comidasLayout.add(comidaLayout);
                }

                categoriaDiv.add(categoriaTitulo, comidasLayout);
                comidasDiv.add(categoriaDiv);
            });
    }

    private void irAAgregarComida() {
        UI.getCurrent().navigate(AgregarComidaView.class);
    }

    private void editarComida(Long comidaId) {
        UI.getCurrent().navigate(EditarComidaView.class, comidaId);
    }

    private void eliminarComida(Long comidaId) {
        // TODO: Agregar diálogo de confirmación
        try {
            comidaService.eliminarComida(comidaId, sesionUsuario.getLoginActual());
            cargarResumen(datePicker.getValue());
        } catch (Exception e) {
            // Mostrar notificación de error
        }
    }

    private void cerrarSesion() {
        sesionUsuario.cerrarSesion();
        UI.getCurrent().navigate(LoginView.class);
    }

}
