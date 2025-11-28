package com.ingesoft.redsocial.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.Alimento;
import com.ingesoft.redsocial.modelo.AlimentoComida;
import com.ingesoft.redsocial.modelo.CategoriaComida;
import com.ingesoft.redsocial.modelo.Comida;
import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.AlimentoComidaRepository;
import com.ingesoft.redsocial.repositorios.ComidaRepository;

@Service
public class ComidaService {

    @Autowired
    private ComidaRepository comidas;

    @Autowired
    private AlimentoComidaRepository alimentosComida;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaComidaService categoriaService;

    @Autowired
    private AlimentoService alimentoService;

    // CU04 - Agregar comida nueva
    @Transactional
    public Comida crearComida(String loginUsuario, Long idCategoria, String nombreComida) throws Exception {
        Usuario usuario = usuarioService.obtenerUsuario(loginUsuario);
        CategoriaComida categoria = categoriaService.obtenerCategoriaPorId(idCategoria);

        Comida comida = new Comida();
        comida.setUsuario(usuario);
        comida.setCategoria(categoria);
        comida.setNombre(nombreComida != null ? nombreComida : categoria.getNombre());
        comida.setFechaHora(LocalDateTime.now());
        comida.setTotalCalorias(0);
        comida.setTotalProteinas(0.0);
        comida.setTotalCarbohidratos(0.0);
        comida.setTotalGrasas(0.0);

        return comidas.save(comida);
    }

    // CU05 y CU06 - Agregar alimento a comida con cantidad
    @Transactional
    public AlimentoComida agregarAlimentoAComida(Long idComida, Long idAlimento, Integer cantidadGramos) 
            throws Exception {
        
        if (cantidadGramos == null || cantidadGramos <= 0) {
            throw new Exception("La cantidad debe ser mayor a cero");
        }

        if (cantidadGramos > 10000) {
            throw new Exception("La cantidad parece incorrecta (más de 10kg)");
        }

        Comida comida = obtenerComidaPorId(idComida);
        Alimento alimento = alimentoService.obtenerAlimentoPorId(idAlimento);

        AlimentoComida alimentoComida = new AlimentoComida();
        alimentoComida.setComida(comida);
        alimentoComida.setAlimento(alimento);
        alimentoComida.setCantidadGramos(cantidadGramos);
        alimentoComida.calcularValoresNutricionales();

        alimentoComida = alimentosComida.save(alimentoComida);

        comida.getAlimentos().add(alimentoComida);
        comida.recalcularTotales();
        comidas.save(comida);

        return alimentoComida;
    }

    // CU07 - Editar comida
    @Transactional
    public Comida editarComida(Long idComida, String loginUsuario, String nuevoNombre, Long nuevaCategoria) 
            throws Exception {
        
        Comida comida = obtenerComidaPorId(idComida);

        if (!comida.getUsuario().getLogin().equals(loginUsuario)) {
            throw new Exception("No tiene permiso para editar esta comida");
        }

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            comida.setNombre(nuevoNombre);
        }

        if (nuevaCategoria != null) {
            CategoriaComida categoria = categoriaService.obtenerCategoriaPorId(nuevaCategoria);
            comida.setCategoria(categoria);
        }

        return comidas.save(comida);
    }

    // Modificar cantidad de alimento en comida
    @Transactional
    public void modificarCantidadAlimento(Long idAlimentoComida, Integer nuevaCantidad) throws Exception {
        if (nuevaCantidad == null || nuevaCantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor a cero");
        }

        AlimentoComida alimentoComida = alimentosComida.findById(idAlimentoComida)
            .orElseThrow(() -> new Exception("No existe el alimento en la comida"));

        alimentoComida.setCantidadGramos(nuevaCantidad);
        alimentoComida.calcularValoresNutricionales();
        alimentosComida.save(alimentoComida);

        Comida comida = alimentoComida.getComida();
        comida.recalcularTotales();
        comidas.save(comida);
    }

    // Eliminar alimento de comida
    @Transactional
    public void eliminarAlimentoDeComida(Long idAlimentoComida) throws Exception {
        AlimentoComida alimentoComida = alimentosComida.findById(idAlimentoComida)
            .orElseThrow(() -> new Exception("No existe el alimento en la comida"));

        Comida comida = alimentoComida.getComida();
        comida.getAlimentos().remove(alimentoComida);
        alimentosComida.delete(alimentoComida);

        comida.recalcularTotales();
        comidas.save(comida);
    }

    // CU08 - Eliminar comida
    @Transactional
    public void eliminarComida(Long idComida, String loginUsuario) throws Exception {
        Comida comida = obtenerComidaPorId(idComida);

        if (!comida.getUsuario().getLogin().equals(loginUsuario)) {
            throw new Exception("No tiene permiso para eliminar esta comida");
        }

        comidas.delete(comida);
    }

    // CU09 - Ver resumen diario
    public Map<CategoriaComida, List<Comida>> obtenerResumenDiario(String loginUsuario, LocalDate fecha) 
            throws Exception {
        
        Usuario usuario = usuarioService.obtenerUsuario(loginUsuario);
        List<Comida> comidasDelDia = comidas.findByUsuarioAndFecha(usuario, fecha);

        return comidasDelDia.stream()
            .collect(Collectors.groupingBy(Comida::getCategoria));
    }

    public ResumenDiarioDTO calcularResumenDiario(String loginUsuario, LocalDate fecha) throws Exception {
        Usuario usuario = usuarioService.obtenerUsuario(loginUsuario);
        List<Comida> comidasDelDia = comidas.findByUsuarioAndFecha(usuario, fecha);

        int totalCalorias = comidasDelDia.stream()
            .mapToInt(Comida::getTotalCalorias)
            .sum();

        double totalProteinas = comidasDelDia.stream()
            .mapToDouble(Comida::getTotalProteinas)
            .sum();

        double totalCarbohidratos = comidasDelDia.stream()
            .mapToDouble(Comida::getTotalCarbohidratos)
            .sum();

        double totalGrasas = comidasDelDia.stream()
            .mapToDouble(Comida::getTotalGrasas)
            .sum();

        Map<CategoriaComida, List<Comida>> comidasPorCategoria = comidasDelDia.stream()
            .collect(Collectors.groupingBy(Comida::getCategoria));

        ResumenDiarioDTO resumen = new ResumenDiarioDTO();
        resumen.setFecha(fecha);
        resumen.setTotalCalorias(totalCalorias);
        resumen.setTotalProteinas(totalProteinas);
        resumen.setTotalCarbohidratos(totalCarbohidratos);
        resumen.setTotalGrasas(totalGrasas);
        resumen.setObjetivoCalorias(usuario.getObjetivoCalorias());
        resumen.setComidasPorCategoria(comidasPorCategoria);

        return resumen;
    }

    public Comida obtenerComidaPorId(Long id) throws Exception {
        return comidas.findById(id)
            .orElseThrow(() -> new Exception("No existe la comida con id: " + id));
    }

    public List<Comida> obtenerComidasDeUsuarioEnRango(String loginUsuario, LocalDate inicio, LocalDate fin) 
            throws Exception {
        Usuario usuario = usuarioService.obtenerUsuario(loginUsuario);
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime finDateTime = fin.atTime(LocalTime.MAX);
        
        return comidas.findByUsuarioAndFechaHoraBetweenOrderByFechaHoraAsc(
            usuario, inicioDateTime, finDateTime);
    }

    // DTO para el resumen diario
    public static class ResumenDiarioDTO {
        private LocalDate fecha;
        private Integer totalCalorias;
        private Double totalProteinas;
        private Double totalCarbohidratos;
        private Double totalGrasas;
        private Integer objetivoCalorias;
        private Map<CategoriaComida, List<Comida>> comidasPorCategoria;

        // Getters y Setters
        public LocalDate getFecha() { return fecha; }
        public void setFecha(LocalDate fecha) { this.fecha = fecha; }
        
        public Integer getTotalCalorias() { return totalCalorias; }
        public void setTotalCalorias(Integer totalCalorias) { this.totalCalorias = totalCalorias; }
        
        public Double getTotalProteinas() { return totalProteinas; }
        public void setTotalProteinas(Double totalProteinas) { this.totalProteinas = totalProteinas; }
        
        public Double getTotalCarbohidratos() { return totalCarbohidratos; }
        public void setTotalCarbohidratos(Double totalCarbohidratos) { 
            this.totalCarbohidratos = totalCarbohidratos; 
        }
        
        public Double getTotalGrasas() { return totalGrasas; }
        public void setTotalGrasas(Double totalGrasas) { this.totalGrasas = totalGrasas; }
        
        public Integer getObjetivoCalorias() { return objetivoCalorias; }
        public void setObjetivoCalorias(Integer objetivoCalorias) { 
            this.objetivoCalorias = objetivoCalorias; 
        }
        
        public Map<CategoriaComida, List<Comida>> getComidasPorCategoria() { 
            return comidasPorCategoria; 
        }
        public void setComidasPorCategoria(Map<CategoriaComida, List<Comida>> comidasPorCategoria) { 
            this.comidasPorCategoria = comidasPorCategoria; 
        }

        public double getPorcentajeObjetivo() {
            if (objetivoCalorias == null || objetivoCalorias == 0) {
                return 0;
            }
            return (totalCalorias * 100.0) / objetivoCalorias;
        }
    }

}
