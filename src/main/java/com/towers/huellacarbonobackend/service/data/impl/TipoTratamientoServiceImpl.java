package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoTratamiento;
import com.towers.huellacarbonobackend.repository.TipoTratamientoRepository;
import com.towers.huellacarbonobackend.service.data.TipoTratamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoTratamientoServiceImpl implements TipoTratamientoService {
    private final TipoTratamientoRepository tipoTratamientoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoTratamiento> getAll() {
        return tipoTratamientoRepository.findAll();
    }
}
