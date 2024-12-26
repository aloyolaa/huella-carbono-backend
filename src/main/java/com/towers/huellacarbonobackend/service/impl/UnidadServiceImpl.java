package com.towers.huellacarbonobackend.service.impl;

import com.towers.huellacarbonobackend.entity.Unidad;
import com.towers.huellacarbonobackend.repository.UnidadRepository;
import com.towers.huellacarbonobackend.service.UnidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadServiceImpl implements UnidadService {
    private final UnidadRepository unidadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Unidad> getAll() {
        return unidadRepository.findAll();
    }
}
