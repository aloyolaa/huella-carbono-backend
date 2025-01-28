package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoRefrigerante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoRefrigeranteRepository extends JpaRepository<TipoRefrigerante, Long> {
    @Query("select t from TipoRefrigerante t where t.nombre = ?1")
    Optional<TipoRefrigerante> findByNombre(String nombre);
}