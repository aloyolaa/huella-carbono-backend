package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.PCGCombustible;
import com.towers.huellacarbonobackend.repository.PCGCombustibleRepository;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PCGCombustibleServiceImpl implements PCGCombustibleService {
    private final PCGCombustibleRepository pcgCombustibleRepository;

    @Override
    @Transactional(readOnly = true)
    public PCGCombustible getByNombre(String nombre) {
        return pcgCombustibleRepository.findByNombre(nombre)
                .orElse(null);
    }
}
