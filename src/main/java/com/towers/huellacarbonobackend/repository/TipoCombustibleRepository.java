package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.TipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCombustibleRepository extends JpaRepository<TipoCombustible, Long> {
}