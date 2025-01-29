package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoPFC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoPFCRepository extends JpaRepository<TipoPFC, Long> {
    @Query("select t from TipoPFC t where t.nombre = ?1")
    Optional<TipoPFC> findByNombre(String nombre);
}