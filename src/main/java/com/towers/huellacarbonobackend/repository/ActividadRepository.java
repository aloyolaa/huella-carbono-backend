package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    @Query("select a from Actividad a where a.archivo.id = ?1 order by a.nombre ASC")
    List<Actividad> findByArchivo(Long id);

}