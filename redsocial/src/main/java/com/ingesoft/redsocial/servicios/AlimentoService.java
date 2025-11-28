package com.ingesoft.redsocial.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.Alimento;
import com.ingesoft.redsocial.repositorios.AlimentoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentos;

    // Inicializar base de datos con alimentos comunes
    @PostConstruct
    @Transactional
    public void inicializarAlimentos() {
        if (alimentos.count() == 0) {
            crearAlimentosDefault();
        }
    }

    private void crearAlimentosDefault() {
        Object[][] alimentosDefault = {
            // nombre, marca, calorias, proteinas, carbohidratos, grasas
            {"Arroz blanco cocido", "Genérico", 130, 2.7, 28.2, 0.3},
            {"Pechuga de pollo", "Genérico", 165, 31.0, 0.0, 3.6},
            {"Huevo", "Genérico", 155, 13.0, 1.1, 11.0},
            {"Pan integral", "Genérico", 247, 13.0, 41.0, 3.4},
            {"Leche descremada", "Genérico", 35, 3.4, 5.0, 0.1},
            {"Plátano", "Genérico", 89, 1.1, 23.0, 0.3},
            {"Manzana", "Genérico", 52, 0.3, 14.0, 0.2},
            {"Avena", "Genérico", 389, 16.9, 66.3, 6.9},
            {"Atún en agua", "Genérico", 116, 25.5, 0.0, 0.8},
            {"Yogur natural", "Genérico", 59, 3.5, 4.7, 3.3},
            {"Queso fresco", "Genérico", 264, 18.0, 3.4, 20.0},
            {"Tomate", "Genérico", 18, 0.9, 3.9, 0.2},
            {"Lechuga", "Genérico", 15, 1.4, 2.9, 0.2},
            {"Zanahoria", "Genérico", 41, 0.9, 9.6, 0.2},
            {"Papa cocida", "Genérico", 77, 2.0, 17.0, 0.1},
            {"Pasta cocida", "Genérico", 131, 5.0, 25.0, 1.1},
            {"Carne molida", "Genérico", 250, 17.0, 0.0, 20.0},
            {"Salmón", "Genérico", 208, 20.0, 0.0, 13.0},
            {"Lentejas cocidas", "Genérico", 116, 9.0, 20.0, 0.4},
            {"Frijoles negros", "Genérico", 132, 8.9, 23.7, 0.5},
            {"Aceite de oliva", "Genérico", 884, 0.0, 0.0, 100.0},
            {"Almendras", "Genérico", 579, 21.0, 22.0, 50.0},
            {"Pan blanco", "Genérico", 265, 8.0, 49.0, 3.2},
            {"Aguacate", "Genérico", 160, 2.0, 9.0, 15.0},
            {"Batata cocida", "Genérico", 86, 1.6, 20.0, 0.1}
        };

        for (Object[] datos : alimentosDefault) {
            Alimento alimento = new Alimento();
            alimento.setNombre((String) datos[0]);
            alimento.setMarca((String) datos[1]);
            alimento.setCaloriasPor100g((Integer) datos[2]);
            alimento.setProteinasPor100g((Double) datos[3]);
            alimento.setCarbohidratosPor100g((Double) datos[4]);
            alimento.setGrasasPor100g((Double) datos[5]);
            alimento.setEsGenerico(true);
            alimentos.save(alimento);
        }
    }

    public List<Alimento> buscarAlimentos(String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return alimentos.findAll();
        }
        return alimentos.buscarAlimentos(busqueda);
    }

    public Alimento obtenerAlimentoPorId(Long id) throws Exception {
        return alimentos.findById(id)
            .orElseThrow(() -> new Exception("No existe el alimento con id: " + id));
    }

    public List<Alimento> obtenerTodosLosAlimentos() {
        return alimentos.findAll();
    }

    public Alimento crearAlimento(Alimento alimento) {
        alimento.setEsGenerico(false);
        return alimentos.save(alimento);
    }

}
