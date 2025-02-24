package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.TipoTratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoTratamientoRepository extends JpaRepository<TipoTratamiento, Long> {
    @Query("select t from TipoTratamiento t where t.nombre = ?1")
    Optional<TipoTratamiento> findByNombre(String nombre);
}