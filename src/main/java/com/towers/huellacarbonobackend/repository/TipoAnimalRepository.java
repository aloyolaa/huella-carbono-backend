package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAnimalRepository extends JpaRepository<TipoAnimal, Long> {
}