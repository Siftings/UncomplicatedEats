package com.ingesoft.redsocial.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    private String login;

    private String nombre;

    private String password;

    private Double pesoActual;

    private Double altura;

    private Integer objetivoCalorias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comida> comidas = new ArrayList<>();

}
