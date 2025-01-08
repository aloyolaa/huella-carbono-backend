package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.TipoCal;
import com.towers.huellacarbonobackend.repository.TipoCalRepository;
import com.towers.huellacarbonobackend.service.data.TipoCalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCalServiceImpl implements TipoCalService {
    private final TipoCalRepository tipoCalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoCal> getAll() {
        return tipoCalRepository.findAll();
    }
}
