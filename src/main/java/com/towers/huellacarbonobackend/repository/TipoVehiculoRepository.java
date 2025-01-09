package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long> {
}