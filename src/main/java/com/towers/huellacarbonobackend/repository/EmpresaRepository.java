package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    @Query("select (count(e) > 0) from Empresa e where e.ruc = ?1")
    boolean existsByRuc(String ruc);

    @Query("select (count(e) > 0) from Empresa e where lower(e.correo) = lower(?1)")
    boolean existsByCorreo(String correo);

    @Query("select (count(e) > 0) from Empresa e where upper(e.razonSocial) = upper(?1)")
    boolean existsByRazonSocial(String razonSocial);
}