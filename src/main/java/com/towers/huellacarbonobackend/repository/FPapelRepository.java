package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FPapel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FPapelRepository extends JpaRepository<FPapel, Long> {
    @Query("select f from FPapel f where f.tipoHoja.id = ?1")
    Optional<FPapel> findByTipoHoja(Long id);
}