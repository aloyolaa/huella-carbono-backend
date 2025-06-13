package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.exception.DataServiceException;
import com.towers.huellacarbonobackend.mapper.DataMapper;
import com.towers.huellacarbonobackend.repository.DatosGeneralesRepository;
import com.towers.huellacarbonobackend.service.data.DataService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DatosGeneralesRepository datosGeneralesRepository;
    private final DataMapper dataMapper;
    private final EmisionCalculatorService emisionCalculatorService;
    private final DetalleManagementService detalleManagementService;

    @Override
    @Transactional
    public DatosGenerales save(DatosGenerales datosGenerales) {
        try {
            datosGenerales.setEmision(emisionCalculatorService.calculateEmision(datosGenerales));
            return datosGeneralesRepository.save(datosGenerales);
        } catch (Exception e) {
            log.error("Error saving DatosGenerales with ID: {}", datosGenerales.getId(), e);
            throw new DataServiceException("Error al guardar datos generales", e);
        }
    }

    @Override
    @Transactional
    public void save(DataDto dataDto, Long empresa, Long archivo) {
        try {
            DatosGenerales datosGenerales = dataMapper.toDatosGenerales(dataDto, empresa, archivo);

            if (datosGenerales.getId() != null) {
                datosGenerales = processExistingDatosGenerales(datosGenerales);
            }

            datosGenerales.setEmision(emisionCalculatorService.calculateEmision(datosGenerales));
            datosGeneralesRepository.save(datosGenerales);

            log.info("Successfully saved DataDto for empresa: {} and archivo: {}", empresa, archivo);

        } catch (Exception e) {
            log.error("Error saving DataDto for empresa: {} and archivo: {}", empresa, archivo, e);
            throw new DataServiceException("Error al procesar y guardar datos", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DatosGenerales getById(Long id) {
        return datosGeneralesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DatosGenerales not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DatosGenerales> getOptionalByEmpresaAndAnio(Long empresaId, Long archivo, Integer anio, Integer mes) {
        return datosGeneralesRepository.findByEmpresaAndAnio(anio, mes, empresaId, archivo);
    }

    @Override
    @Transactional(readOnly = true)
    public DataDto getByEmpresaAndAnio(Long empresaId, Long archivo, Integer anio, Integer mes) {
        return getOptionalByEmpresaAndAnio(empresaId, archivo, anio, mes)
                .map(dataMapper::toDataDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No hay datos registrados para el año %d del mes %d, empresa %d, archivo %d",
                                anio, mes, empresaId, archivo)));
    }

    @Override
    @Transactional(readOnly = true)
    public DatosGenerales getByArchivoAndAnio(Long empresa, Long archivo, Integer anio, Integer mes) {
        return datosGeneralesRepository.findByEmpresaAndAnio(anio, mes, empresa, archivo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No hay datos registrados para el año %d, mes %d, empresa %d, archivo %d",
                                anio, mes, empresa, archivo)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            if (!datosGeneralesRepository.existsById(id)) {
                throw new EntityNotFoundException("DatosGenerales not found with ID: " + id);
            }
            datosGeneralesRepository.deleteById(id);
            log.info("Successfully deleted DatosGenerales with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting DatosGenerales with ID: {}", id, e);
            throw new DataServiceException("Error al eliminar datos generales", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatosGenerales> getByAnioAndEmpresa(Integer anio, Long empresaId) {
        return datosGeneralesRepository.findByAnioAndEmpresa(anio, empresaId);
    }

    private DatosGenerales processExistingDatosGenerales(DatosGenerales newDatosGenerales) {
        return datosGeneralesRepository.findById(newDatosGenerales.getId())
                .map(existing -> {
                    detalleManagementService.updateDetalles(existing, newDatosGenerales.getDetalles());
                    newDatosGenerales.setDetalles(existing.getDetalles());
                    return newDatosGenerales;
                })
                .orElse(newDatosGenerales);
    }
}
