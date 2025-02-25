package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.entity.data.Archivo;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.EmpresaArchivo;
import com.towers.huellacarbonobackend.mapper.ArchivoMapper;
import com.towers.huellacarbonobackend.repository.EmpresaArchivoRepository;
import com.towers.huellacarbonobackend.service.data.EmpresaArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaArchivoServiceImpl implements EmpresaArchivoService {
    private final ArchivoMapper archivoMapper;
    private final EmpresaArchivoRepository empresaArchivoRepository;

    @Override
    @Transactional
    public void save(List<Integer> archivos, Long id, Integer anio) {
        List<EmpresaArchivo> list = archivos.stream()
                .map(a -> {
                    EmpresaArchivo empresaArchivo = new EmpresaArchivo();
                    empresaArchivo.setEmpresa(new Empresa(id));
                    empresaArchivo.setArchivo(new Archivo(a.longValue()));
                    empresaArchivo.setAnio(anio);
                    return empresaArchivo;
                }).toList();
        empresaArchivoRepository.saveAll(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArchivoDto> getArchivosByAnio(Long id, Integer anio) {
        return empresaArchivoRepository.findByEmpresaAndAnio(id, anio)
                .stream()
                .map(e -> archivoMapper.toArchivoDto(e.getArchivo()))
                .toList();
    }
}
