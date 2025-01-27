package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    @Query("select s from Seccion s where s.nombre = ?1")
    Optional<Seccion> findByNombre(String nombre);

    @Query("select s from Seccion s where s.nombre like concat('%', ?1, '%')")
    Optional<Seccion> findByNombreContains(String nombre);
}