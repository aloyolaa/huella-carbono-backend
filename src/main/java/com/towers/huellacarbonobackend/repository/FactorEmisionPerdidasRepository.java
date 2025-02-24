package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionPerdidas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FactorEmisionPerdidasRepository extends JpaRepository<FactorEmisionPerdidas, Long> {
    @Query("select f from FactorEmisionPerdidas f where f.anio = ?1")
    Optional<FactorEmisionPerdidas> findByAnio(Integer anio);
}