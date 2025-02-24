package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FactorEmisionConsumoRepository extends JpaRepository<FactorEmisionConsumo, Long> {
    @Query("select f from FactorEmisionConsumo f where f.anio = ?1")
    Optional<FactorEmisionConsumo> findByAnio(Integer anio);
}