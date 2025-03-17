package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.calculate.FCMCondicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FCMCondicionRepository extends JpaRepository<FCMCondicion, Long> {
    @Query("select f from FCMCondicion f where f.condicionSEDS.id = ?1")
    Optional<FCMCondicion> findByCondicionSEDS(Long id);
}