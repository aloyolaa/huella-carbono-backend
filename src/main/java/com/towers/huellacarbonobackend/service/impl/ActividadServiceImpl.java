package com.towers.huellacarbonobackend.service.impl;

import com.towers.huellacarbonobackend.dto.ActividadDto;
import com.towers.huellacarbonobackend.mapper.ActividadMapper;
import com.towers.huellacarbonobackend.repository.ActividadRepository;
import com.towers.huellacarbonobackend.service.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActividadServiceImpl implements ActividadService {
    private final ActividadRepository actividadRepository;
    private final ActividadMapper actividadMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ActividadDto> getAllByArchivo(Long archivoId) {
        return actividadRepository.findByArchivo(archivoId).stream().map(actividadMapper::toActividadDto).toList();
    }
}
