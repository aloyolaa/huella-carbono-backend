package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoHoja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoHojaRepository extends JpaRepository<TipoHoja, Long> {
    @Query("select t from TipoHoja t where t.nombre = ?1")
    Optional<TipoHoja> findByNombre(String nombre);
}