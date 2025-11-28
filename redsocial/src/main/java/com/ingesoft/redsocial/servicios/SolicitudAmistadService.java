package com.ingesoft.redsocial.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.CategoriaComida;
import com.ingesoft.redsocial.repositorios.CategoriaComidaRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CategoriaComidaService {

    @Autowired
    private CategoriaComidaRepository categorias;

    // CU03 - Crear categorías de comidas predeterminadas
    @PostConstruct
    @Transactional
    public void inicializarCategorias() {
        if (categorias.count() == 0) {
            crearCategoriasDefault();
        }
    }

    private void crearCategoriasDefault() {
        String[][] categoriasDefault = {
            {"Desayuno", "Primera comida del día", "1"},
            {"Media mañana", "Snack entre desayuno y almuerzo", "2"},
            {"Almuerzo", "Comida principal del día", "3"},
            {"Merienda", "Snack de la tarde", "4"},
            {"Cena", "Última comida del día", "5"},
            {"Snacks", "Colaciones y bocadillos", "6"}
        };

        for (String[] datos : categoriasDefault) {
            CategoriaComida categoria = new CategoriaComida();
            categoria.setNombre(datos[0]);
            categoria.setDescripcion(datos[1]);
            categoria.setOrden(Integer.parseInt(datos[2]));
            categorias.save(categoria);
        }
    }

    public List<CategoriaComida> obtenerTodasLasCategorias() {
        return categorias.findAllByOrderByOrdenAsc();
    }

    public CategoriaComida obtenerCategoriaPorId(Long id) throws Exception {
        return categorias.findById(id)
            .orElseThrow(() -> new Exception("No existe la categoría con id: " + id));
    }

    public CategoriaComida obtenerCategoriaPorNombre(String nombre) throws Exception {
        return categorias.findByNombre(nombre)
            .orElseThrow(() -> new Exception("No existe la categoría: " + nombre));
    }

}
