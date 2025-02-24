package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.TipoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TipoAnimalRepository extends JpaRepository<TipoAnimal, Long> {
    @Query("select t from TipoAnimal t where t.nombre = ?1")
    Optional<TipoAnimal> findByNombre(String nombre);
}