package com.ingesoft.redsocial.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class SolicitudAmistad {

    @Id
    Long id;

    Boolean aceptado;

    LocalDate fechaSolicitud;

    LocalDate fechaRespuesta;

    @ManyToOne
    Usuario remitente;

    @ManyToOne
    Usuario destinatario;

}
