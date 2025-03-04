package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.mapper.DataMapper;
import com.towers.huellacarbonobackend.repository.DatosGeneralesRepository;
import com.towers.huellacarbonobackend.service.data.DataService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DatosGeneralesRepository datosGeneralesRepository;
    private final DataMapper dataMapper;

    @Override
    @Transactional
    public DatosGenerales save(DatosGenerales datosGenerales) {
        return datosGeneralesRepository.save(datosGenerales);
    }

    @Override
    @Transactional
    public void save(DataDto dataDto, Long empresa, Long archivo) {
        datosGeneralesRepository.save(dataMapper.toDatosGenerales(dataDto, empresa, archivo));
    }

    @Override
    @Transactional(readOnly = true)
    public DatosGenerales getById(Long id) {
        return datosGeneralesRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DatosGenerales> getOptionalByEmpresaAndAnio(Long empresaId, Long archivo, Integer anio) {
        return datosGeneralesRepository.findByEmpresaAndAnio(anio, empresaId, archivo);
    }

    @Override
    @Transactional(readOnly = true)
    public DataDto getByEmpresaAndAnio(Long empresaId, Long archivo, Integer anio) {
        return getOptionalByEmpresaAndAnio(empresaId, archivo, anio)
                .map(dataMapper::toDataDto)
                .orElseThrow(() -> new EntityNotFoundException("No hay datos registrados para el año " + anio));
    }

    @Override
    @Transactional(readOnly = true)
    public DatosGenerales getByArchivoAndAnio(Long empresa, Long archivo, Integer anio) {
        return datosGeneralesRepository.findByEmpresaAndAnio(anio, empresa, archivo)
                .orElseThrow(() -> new EntityNotFoundException("No hay datos registrados para el año " + anio));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        datosGeneralesRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatosGenerales> getByAnioAndEmpresa(Integer anio, Long id) {
        return datosGeneralesRepository.findByAnioAndEmpresa(anio, id);
    }
}
