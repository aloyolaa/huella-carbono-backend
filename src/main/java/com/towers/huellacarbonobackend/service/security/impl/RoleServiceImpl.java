package com.towers.huellacarbonobackend.service.security.impl;

import com.towers.huellacarbonobackend.entity.Role;
import com.towers.huellacarbonobackend.exception.DataAccessExceptionImpl;
import com.towers.huellacarbonobackend.repository.RoleRepository;
import com.towers.huellacarbonobackend.service.security.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAll() {
        try {
            return roleRepository.findAll();
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }
}
