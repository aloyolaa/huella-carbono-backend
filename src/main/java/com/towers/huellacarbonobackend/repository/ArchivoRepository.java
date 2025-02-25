package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
}