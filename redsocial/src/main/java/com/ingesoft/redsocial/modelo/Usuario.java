package com.ingesoft.redsocial.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    String login;

    String nombre;

    String password;

    @OneToMany(mappedBy = "remitente")
    List<SolicitudAmistad> solicitudesEnviadas;

    @OneToMany(mappedBy = "destinatario")
    List<SolicitudAmistad> solicitudesRecibidas;

}
