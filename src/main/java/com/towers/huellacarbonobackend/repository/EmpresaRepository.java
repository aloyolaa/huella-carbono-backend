package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
  }