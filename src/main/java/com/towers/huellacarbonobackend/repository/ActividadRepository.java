package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    @Query("select a from Actividad a where a.archivo.id = ?1 order by a.nombre ASC")
    List<Actividad> findByArchivo(Long id);

    @Query("select a from Actividad a where a.nombre = ?1 and a.archivo.id = ?2 and a.seccion.id = ?3 and a.accion.id = ?4")
    Optional<Actividad> findByNombreAndArchivoAndSeccionAndAccion(String nombre, Long archivoId, Long seccionId, Long accionId);

    @Query("select a from Actividad a where a.nombre = ?1 and a.archivo.id = ?2 and a.seccion.id = ?3")
    Optional<Actividad> findByNombreAndArchivoAndSeccion(String nombre, Long id, Long id1);
}