package com.ingesoft.redsocial.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.redsocial.modelo.AlimentoComida;
import com.ingesoft.redsocial.modelo.Comida;

@Repository
public interface AlimentoComidaRepository extends JpaRepository<AlimentoComida, Long> {

    List<AlimentoComida> findByComida(Comida comida);

    void deleteByComida(Comida comida);

}
