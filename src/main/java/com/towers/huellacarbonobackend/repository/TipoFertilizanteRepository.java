package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.TipoFertilizante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoFertilizanteRepository extends JpaRepository<TipoFertilizante, Long> {
    @Query("select t from TipoFertilizante t where t.nombre = ?1")
    Optional<TipoFertilizante> findByNombre(String nombre);
}