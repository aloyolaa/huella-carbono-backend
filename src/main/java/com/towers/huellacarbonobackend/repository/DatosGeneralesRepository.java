package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.DatosGenerales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatosGeneralesRepository extends JpaRepository<DatosGenerales, Long> {
}