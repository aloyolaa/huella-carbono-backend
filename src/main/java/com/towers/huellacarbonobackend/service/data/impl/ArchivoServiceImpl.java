package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.mapper.ArchivoMapper;
import com.towers.huellacarbonobackend.repository.ArchivoRepository;
import com.towers.huellacarbonobackend.service.data.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArchivoServiceImpl implements ArchivoService {
    private final ArchivoRepository archivoRepository;
    private final ArchivoMapper archivoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ArchivoDto> getAll() {
        return archivoRepository.findAll().stream()
                .map(archivoMapper::toArchivoDto)
                .toList();
    }
}
