package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
    @Query("select z from Zona z where z.nombre = ?1")
    Optional<Zona> findByNombre(String nombre);
}