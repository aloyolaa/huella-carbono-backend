package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    @Query("select (count(e) > 0) from Empresa e where e.ruc = ?1")
    boolean existsByRuc(String ruc);

    @Query("select (count(e) > 0) from Empresa e where e.correo = ?1")
    boolean existsByCorreo(String correo);
}