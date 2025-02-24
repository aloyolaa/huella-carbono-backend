package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.ActividadDto;
import com.towers.huellacarbonobackend.entity.data.Actividad;
import com.towers.huellacarbonobackend.mapper.ActividadMapper;
import com.towers.huellacarbonobackend.repository.ActividadRepository;
import com.towers.huellacarbonobackend.service.data.ActividadService;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    @Transactional(readOnly = true)
    public Actividad getByNombreAndArchivoAndSeccionAndAccion(String nombre, Long archivoId, Long seccionId, Long accionId) {
        return actividadRepository.findByNombreAndArchivoAndSeccionAndAccion(nombre, archivoId, seccionId, accionId)
                .orElseThrow(() -> new EntityNotFoundException("Actividad " + nombre + " no encontrado."));
    }

    @Override
    public Actividad getByNombreAndArchivoAndSeccion(String nombre, Long archivoId, Long seccionId) {
        return actividadRepository.findByNombreAndArchivoAndSeccion(nombre, archivoId, seccionId)
                .orElseThrow(() -> new EntityNotFoundException("Actividad " + nombre + " no encontrado."));
    }
}
