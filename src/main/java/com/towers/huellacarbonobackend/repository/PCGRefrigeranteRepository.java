package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.PCGRefrigerante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PCGRefrigeranteRepository extends JpaRepository<PCGRefrigerante, Long> {
    @Query("select p from PCGRefrigerante p where p.tipoRefrigerante.id = ?1")
    Optional<PCGRefrigerante> findByTipoRefrigerante(Long id);
}