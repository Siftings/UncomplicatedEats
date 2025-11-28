package com.ingesoft.redsocial.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Comida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private LocalDateTime fechaHora;

    private Integer totalCalorias = 0;

    private Double totalProteinas = 0.0;

    private Double totalCarbohidratos = 0.0;

    private Double totalGrasas = 0.0;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private CategoriaComida categoria;

    @OneToMany(mappedBy = "comida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlimentoComida> alimentos = new ArrayList<>();

    /**
     * Recalcula todos los totales nutricionales sumando los alimentos
     */
    public void recalcularTotales() {
        this.totalCalorias = alimentos.stream()
            .mapToInt(AlimentoComida::getCaloriasCalculadas)
            .sum();
        
        this.totalProteinas = alimentos.stream()
            .mapToDouble(AlimentoComida::getProteinasCalculadas)
            .sum();
        
        this.totalCarbohidratos = alimentos.stream()
            .mapToDouble(AlimentoComida::getCarbohidratosCalculados)
            .sum();
        
        this.totalGrasas = alimentos.stream()
            .mapToDouble(AlimentoComida::getGrasasCalculadas)
            .sum();
    }

}
