package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DatosGeneralesRepository extends JpaRepository<DatosGenerales, Long> {
    @Query("select d from DatosGenerales d where d.anio = ?1 and d.mes = ?2 and d.empresa.id = ?3 and d.archivo.id = ?4")
    Optional<DatosGenerales> findByEmpresaAndAnio(Integer anio, Integer mes, Long empresa, Long archivo);

    @Query("select d from DatosGenerales d where d.anio = ?1 and d.empresa.id = ?2 order by d.archivo.id")
    List<DatosGenerales> findByAnioAndEmpresa(Integer anio, Long id);


}