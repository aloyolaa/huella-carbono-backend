package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.PotencialCalentamientoGlobal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PotencialCalentamientoGlobalRepository extends JpaRepository<PotencialCalentamientoGlobal, Long> {
    @Query("select p from PotencialCalentamientoGlobal p where p.nombre = ?1")
    Optional<PotencialCalentamientoGlobal> findByNombre(String nombre);
}