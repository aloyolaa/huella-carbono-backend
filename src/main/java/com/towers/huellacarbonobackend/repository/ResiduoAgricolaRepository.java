package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.ResiduoAgricola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResiduoAgricolaRepository extends JpaRepository<ResiduoAgricola, Long> {
    @Query("select r from ResiduoAgricola r where r.nombre = ?1")
    Optional<ResiduoAgricola> findByNombre(String nombre);
}