package com.ingesoft.redsocial.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.redsocial.modelo.CategoriaComida;

@Repository
public interface CategoriaComidaRepository extends JpaRepository<CategoriaComida, Long> {

    List<CategoriaComida> findAllByOrderByOrdenAsc();

    Optional<CategoriaComida> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}
