package com.ingesoft.redsocial.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ingesoft.redsocial.modelo.SolicitudAmistad;
import com.ingesoft.redsocial.modelo.Usuario;

@Repository
public interface SolicitudAmistadRepository extends JpaRepository<SolicitudAmistad, Long> {

    List<SolicitudAmistad> findByRemitenteAndAceptadoIsNull(Usuario remitente);

    List<SolicitudAmistad> findByDestinatarioAndAceptadoIsNull(Usuario destinatario);

    @Query("""
        select remitente 
            from SolicitudAmistad solicitud
            inner join solicitud.remitente remitente
            inner join solicitud.destinatario destinatario
        where
            solicitud.aceptado = true
            and destinatario.login = ?1
        union
            select destinatario 
                from SolicitudAmistad solicitud
                inner join solicitud.remitente remitente
                inner join solicitud.destinatario destinatario
            where
                solicitud.aceptado = true
                and remitente.login = ?1
    """)
    List<Usuario> findAmigosById(String login);

}
