package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.TipoCombustibleDto;
import com.towers.huellacarbonobackend.mapper.TipoCombustibleMapper;
import com.towers.huellacarbonobackend.repository.TipoCombustibleRepository;
import com.towers.huellacarbonobackend.service.data.TipoCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCombustibleServiceImpl implements TipoCombustibleService {
    private final TipoCombustibleRepository tipoCombustibleRepository;
    private final TipoCombustibleMapper tipoCombustibleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TipoCombustibleDto> getAllByArchivo(Long archivoId) {
        archivoId = archivoId == 2 || archivoId == 21 ? 1 : archivoId;
        return tipoCombustibleRepository.findByArchivo(archivoId).stream().map(tipoCombustibleMapper::toTipoCombustibleDto).toList();
    }
}
