package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.TipoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoEquipoRepository extends JpaRepository<TipoEquipo, Long> {
    @Query("select t from TipoEquipo t where t.nombre = ?1")
    Optional<TipoEquipo> findByNombre(String nombre);
}