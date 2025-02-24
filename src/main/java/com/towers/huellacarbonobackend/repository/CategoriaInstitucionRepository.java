package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.CategoriaInstitucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoriaInstitucionRepository extends JpaRepository<CategoriaInstitucion, Long> {
    @Query("select c from CategoriaInstitucion c where c.nombre = ?1")
    Optional<CategoriaInstitucion> findByNombre(String nombre);
}