package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.exception.DataAccessExceptionImpl;
import com.towers.huellacarbonobackend.mapper.ArchivoMapper;
import com.towers.huellacarbonobackend.repository.EmpresaRepository;
import com.towers.huellacarbonobackend.service.data.EmpresaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final ArchivoMapper archivoMapper;

    @Override
    @Transactional(readOnly = true)
    public Empresa getById(Long id) {
        try {
            return empresaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Empresa con el ID " + id + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    public List<ArchivoDto> getArchivos(Long id) {
        Empresa byId = getById(id);
        return byId.getArchivos().stream()
                .map(archivoMapper::toArchivoDto)
                .sorted(Comparator.comparingLong(ArchivoDto::id))
                .toList();
    }
}
