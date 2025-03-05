package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.PCGCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PCGCombustibleRepository extends JpaRepository<PCGCombustible, Long> {
    @Query("select p from PCGCombustible p where p.nombre = ?1")
    Optional<PCGCombustible> findByNombre(String nombre);
}