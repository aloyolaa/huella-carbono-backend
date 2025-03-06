package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FEVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FEVehiculoRepository extends JpaRepository<FEVehiculo, Long> {
    @Query("select f from FEVehiculo f where f.tipoTransporte.id = ?1")
    Optional<FEVehiculo> findByTipoTransporte(Long id);
}