package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.TokenRestablecimiento;
import com.towers.huellacarbonobackend.entity.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRestablecimientoRepository extends JpaRepository<TokenRestablecimiento, Long> {
    @Query("select t from TokenRestablecimiento t where t.token = ?1")
    Optional<TokenRestablecimiento> findByToken(String token);

    @Transactional
    @Modifying
    @Query("delete from TokenRestablecimiento t where t.usuario = ?1")
    void deleteByUsuario(Usuario usuario);
}