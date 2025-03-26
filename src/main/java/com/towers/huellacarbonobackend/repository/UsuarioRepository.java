package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select (count(u) > 0) from Usuario u where upper(u.username) = upper(?1)")
    boolean existsByUsername(String username);

    @Query("select (count(u) > 0) from Usuario u where lower(u.correo) = lower(?1)")
    boolean existsByCorreo(String correo);

    @Query("select u from Usuario u where upper(u.username) = upper(?1)")
    Optional<Usuario> findByUsername(String username);
}