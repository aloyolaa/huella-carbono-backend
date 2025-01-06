package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipoCombustibleRepository extends JpaRepository<TipoCombustible, Long> {
    @Query("select t from TipoCombustible t where t.archivo.id = ?1 order by t.nombre ASC")
    List<TipoCombustible> findByArchivo(Long id);

}