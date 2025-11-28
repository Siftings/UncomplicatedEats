package com.ingesoft.redsocial.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String marca;

    private Integer caloriasPor100g;

    private Double proteinasPor100g;

    private Double carbohidratosPor100g;

    private Double grasasPor100g;

    private Boolean esGenerico = true;

    @OneToMany(mappedBy = "alimento", cascade = CascadeType.ALL)
    private List<AlimentoComida> alimentosComida = new ArrayList<>();

}
