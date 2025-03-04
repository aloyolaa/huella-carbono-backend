package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.EmpresaArchivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpresaArchivoRepository extends JpaRepository<EmpresaArchivo, Long> {
    @Query("select e from EmpresaArchivo e where e.empresa.id = ?1 and e.anio = ?2")
    List<EmpresaArchivo> findByEmpresaAndAnio(Long id, Integer anio);

    @Query("select (count(e) > 0) from EmpresaArchivo e where e.empresa.id = ?1")
    boolean existsByEmpresa(Long id);
}