package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FactorConversionCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FactorConversionCombustibleRepository extends JpaRepository<FactorConversionCombustible, Long> {
    @Query("select f from FactorConversionCombustible f where f.tipoCombustible.id = ?1")
    Optional<FactorConversionCombustible> findByTipoCombustible(Long id);
}