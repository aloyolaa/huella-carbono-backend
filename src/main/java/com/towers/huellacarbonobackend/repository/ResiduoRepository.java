package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.Residuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResiduoRepository extends JpaRepository<Residuo, Long> {
    @Query("select r from Residuo r where r.nombre = ?1")
    Optional<Residuo> findByNombre(String nombre);
}