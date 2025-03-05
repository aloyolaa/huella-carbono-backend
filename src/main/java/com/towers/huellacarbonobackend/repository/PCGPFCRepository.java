package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.PCGPFC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PCGPFCRepository extends JpaRepository<PCGPFC, Long> {
  @Query("select p from PCGPFC p where p.tipoPFC.id = ?1")
  Optional<PCGPFC> findByTipoPFC(Long id);

}