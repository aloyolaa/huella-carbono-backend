package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FEMovilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FEMovilidadRepository extends JpaRepository<FEMovilidad, Long> {
    @Query("select f from FEMovilidad f where f.tipoMovilidad.id = ?1")
    Optional<FEMovilidad> findByTipoMovilidad(Long id);

}