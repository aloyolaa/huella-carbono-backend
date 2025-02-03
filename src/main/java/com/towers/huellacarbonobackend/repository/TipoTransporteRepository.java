package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoTransporteRepository extends JpaRepository<TipoTransporte, Long> {
    @Query("select t from TipoTransporte t where t.nombre = ?1")
    Optional<TipoTransporte> findByNombre(String nombre);
}