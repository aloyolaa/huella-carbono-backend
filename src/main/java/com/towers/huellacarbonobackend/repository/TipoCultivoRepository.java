package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.TipoCultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoCultivoRepository extends JpaRepository<TipoCultivo, Long> {
    @Query("select t from TipoCultivo t where t.nombre = ?1")
    Optional<TipoCultivo> findByNombre(String nombre);
}