package com.ingesoft.redsocial.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.redsocial.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    public List<Usuario> findByNombreContains(String nombre);

}
