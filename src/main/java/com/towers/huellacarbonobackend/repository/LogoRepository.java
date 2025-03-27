package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.data.Logo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LogoRepository extends JpaRepository<Logo, Long> {
    @Query("select l from Logo l where l.empresa.id = ?1")
    Optional<Logo> findByEmpresa(Long id);
}