package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.Accion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccionRepository extends JpaRepository<Accion, Long> {
    @Query("select a from Accion a where a.nombre = ?1")
    Optional<Accion> findByNombre(String nombre);
}