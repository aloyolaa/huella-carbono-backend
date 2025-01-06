package com.towers.huellacarbonobackend.repository;

import com.towers.huellacarbonobackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}