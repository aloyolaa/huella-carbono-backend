package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.DatosGenerales;
import com.towers.huellacarbonobackend.mapper.DataMapper;
import com.towers.huellacarbonobackend.repository.DatosGeneralesRepository;
import com.towers.huellacarbonobackend.service.data.DataService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
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
        System.out.println("id: " + dataDto.id());
        datosGeneralesRepository.save(dataMapper.toDatosGenerales(dataDto, empresa, archivo));
    }

    @Override
    @Transactional(readOnly = true)
    public DatosGenerales getById(Long id) {
        return datosGeneralesRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DatosGenerales> getOptionalByEmpresaAndAnio(Long empresaId, Long archivo) {
        return datosGeneralesRepository.findByEmpresaAndAnio(Year.now().getValue(), empresaId, archivo);
    }

    @Override
    @Transactional(readOnly = true)
    public DataDto getByEmpresaAndAnio(Long empresaId, Long archivo) {
        return getOptionalByEmpresaAndAnio(empresaId, archivo)
                .map(dataMapper::toDataDto)
                .orElseThrow(() -> new EntityNotFoundException("No hay datos registrados para el presente año."));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        datosGeneralesRepository.deleteById(id);
    }
}
