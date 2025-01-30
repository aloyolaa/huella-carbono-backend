package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoCal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoCalRepository extends JpaRepository<TipoCal, Long> {
    @Query("select t from TipoCal t where t.nombre = ?1")
    Optional<TipoCal> findByNombre(String nombre);
}