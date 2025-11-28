package com.ingesoft.redsocial.repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ingesoft.redsocial.modelo.Comida;
import com.ingesoft.redsocial.modelo.Usuario;

@Repository
public interface ComidaRepository extends JpaRepository<Comida, Long> {

    List<Comida> findByUsuarioAndFechaHoraBetweenOrderByFechaHoraAsc(
        Usuario usuario, 
        LocalDateTime inicio, 
        LocalDateTime fin
    );

    @Query("""
        SELECT c FROM Comida c 
        WHERE c.usuario = :usuario 
        AND DATE(c.fechaHora) = :fecha 
        ORDER BY c.categoria.orden ASC, c.fechaHora ASC
    """)
    List<Comida> findByUsuarioAndFecha(
        @Param("usuario") Usuario usuario, 
        @Param("fecha") LocalDate fecha
    );

    @Query("""
        SELECT SUM(c.totalCalorias) FROM Comida c 
        WHERE c.usuario = :usuario 
        AND DATE(c.fechaHora) = :fecha
    """)
    Integer sumCaloriasByUsuarioAndFecha(
        @Param("usuario") Usuario usuario, 
        @Param("fecha") LocalDate fecha
    );

}
