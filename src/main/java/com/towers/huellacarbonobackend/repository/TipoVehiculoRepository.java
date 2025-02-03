package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long> {
    @Query("select t from TipoVehiculo t where t.nombre = ?1")
    Optional<TipoVehiculo> findByNombre(String nombre);
}