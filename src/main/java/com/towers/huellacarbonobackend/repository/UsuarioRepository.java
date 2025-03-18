package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select (count(u) > 0) from Usuario u where u.username = ?1")
    boolean existsByUsername(String username);

    @Query("select u from Usuario u where upper(u.username) = upper(?1)")
    Optional<Usuario> findByUsername(String username);

    @Query("select max(u.id) from Usuario")
    long maxId();
}