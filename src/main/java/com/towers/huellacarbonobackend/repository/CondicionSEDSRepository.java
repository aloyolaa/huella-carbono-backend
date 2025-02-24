package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.CondicionSEDS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CondicionSEDSRepository extends JpaRepository<CondicionSEDS, Long> {
    @Query("select c from CondicionSEDS c where c.nombre = ?1")
    Optional<CondicionSEDS> findByNombre(String nombre);
}