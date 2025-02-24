package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.TipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TipoCombustibleRepository extends JpaRepository<TipoCombustible, Long> {
    @Query("select t from TipoCombustible t where t.archivo.id = ?1 order by t.nombre ASC")
    List<TipoCombustible> findByArchivo(Long archivoId);

    @Query("select t from TipoCombustible t where upper(t.nombre) = upper(?1) and t.archivo.id = ?2")
    Optional<TipoCombustible> findByNombreAndArchivo(String nombre, Long archivoId);

    @Query("select t from TipoCombustible t where t.nombre = ?1 and t.archivo.id = ?2 and t.seccion.id = ?3")
    Optional<TipoCombustible> findByNombreAndArchivoAndSeccion(String nombre, Long archivoId, Long seccionId);
}