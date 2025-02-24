package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.data.Residuo;
import com.towers.huellacarbonobackend.repository.ResiduoRepository;
import com.towers.huellacarbonobackend.service.data.ResiduoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResiduoServiceImpl implements ResiduoService {
    private final ResiduoRepository residuoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Residuo> getAll() {
        return residuoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Residuo getByNombre(String nombre) {
        return residuoRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Residuo " + nombre + " no encontrado."));
    }
}
