package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.DatosGenerales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DatosGeneralesRepository extends JpaRepository<DatosGenerales, Long> {
    @Query("select d from DatosGenerales d where d.anio = ?1 and d.empresa.id = ?2 and d.archivo.id = ?3")
    Optional<DatosGenerales> findByEmpresaAndAnio(Integer anio, Long id, Long id1);
}