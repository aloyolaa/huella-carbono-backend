package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FactorEmisionCombustibleRepository extends JpaRepository<FactorEmisionCombustible, Long> {
    @Query("select f from FactorEmisionCombustible f where f.tipoCombustible.id = ?1")
    Optional<FactorEmisionCombustible> findByTipoCombustible(Long id);

}