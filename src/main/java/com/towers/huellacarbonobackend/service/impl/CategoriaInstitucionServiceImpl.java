package com.towers.huellacarbonobackend.service.impl;

import com.towers.huellacarbonobackend.entity.CategoriaInstitucion;
import com.towers.huellacarbonobackend.repository.CategoriaInstitucionRepository;
import com.towers.huellacarbonobackend.service.CategoriaInstitucionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaInstitucionServiceImpl implements CategoriaInstitucionService {
    private final CategoriaInstitucionRepository categoriaInstitucionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaInstitucion> getAll() {
        return categoriaInstitucionRepository.findAll();
    }
}
