package com.ingesoft.redsocial.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ingesoft.redsocial.modelo.Alimento;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

    List<Alimento> findByNombreContainingIgnoreCase(String nombre);

    List<Alimento> findByMarcaContainingIgnoreCase(String marca);

    @Query("""
        SELECT a FROM Alimento a 
        WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) 
        OR LOWER(a.marca) LIKE LOWER(CONCAT('%', :busqueda, '%'))
    """)
    List<Alimento> buscarAlimentos(@Param("busqueda") String busqueda);

    List<Alimento> findByEsGenerico(Boolean esGenerico);

}
