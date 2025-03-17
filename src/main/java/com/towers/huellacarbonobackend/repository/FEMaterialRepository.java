package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FEMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FEMaterialRepository extends JpaRepository<FEMaterial, Long> {
    @Query("select f from FEMaterial f where f.tipoVehiculo.id = ?1")
    Optional<FEMaterial> findByTipoVehiculo(Long id);
}