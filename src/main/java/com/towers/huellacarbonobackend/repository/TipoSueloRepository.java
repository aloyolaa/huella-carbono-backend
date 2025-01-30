package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoSuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoSueloRepository extends JpaRepository<TipoSuelo, Long> {
    @Query("select t from TipoSuelo t where t.nombre = ?1")
    Optional<TipoSuelo> findByNombre(String nombre);
}