package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.mapper.DataMapper;
import com.towers.huellacarbonobackend.repository.DatosGeneralesRepository;
import com.towers.huellacarbonobackend.service.calculate.format.*;
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
    private final EnergiaYCombustionCalculate energiaYCombustionCalculate;
    private final FuentesMovilesYRefinacionCalculate fuentesMovilesYRefinacionCalculate;
    private final RefrigerantesCalculate refrigerantesCalculate;
    private final FugasSF6Calculate fugasSF6Calculate;
    private final PFCCalculate pfcCalculate;
    private final ConsumoElectricidadCalculate consumoElectricidadCalculate;
    private final PerdidasCalculate perdidasCalculate;
    private final TransporteAereoCalculate transporteAereoCalculate;
    private final TransporteTerrestreCalculate transporteTerrestreCalculate;

    @Override
    @Transactional
    public DatosGenerales save(DatosGenerales datosGenerales) {
        datosGenerales.setEmision(getEmision(datosGenerales));
        return datosGeneralesRepository.save(datosGenerales);
    }

    @Override
    @Transactional
    public void save(DataDto dataDto, Long empresa, Long archivo) {
        DatosGenerales datosGenerales = dataMapper.toDatosGenerales(dataDto, empresa, archivo);
        datosGenerales.setEmision(getEmision(datosGenerales));
        datosGeneralesRepository.save(datosGenerales);
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

    private double getEmision(DatosGenerales datosGenerales) {
        return switch (datosGenerales.getArchivo().getId().intValue()) {
            case 1, 2, 21 -> energiaYCombustionCalculate.calculate(datosGenerales);
            case 3 -> fuentesMovilesYRefinacionCalculate.calculate(datosGenerales); // TODO falta agregar factor de conversión y factor de emisión
            case 8 -> refrigerantesCalculate.calculate(datosGenerales);
            case 9 -> fugasSF6Calculate.calculate(datosGenerales);
            case 10 -> pfcCalculate.calculate(datosGenerales);
            case 18 -> consumoElectricidadCalculate.calculate(datosGenerales);
            case 19, 20 -> perdidasCalculate.calculate(datosGenerales);
            case 24 -> transporteAereoCalculate.calculate(datosGenerales);
            case 25 -> transporteTerrestreCalculate.calculate(datosGenerales);
            default -> 0.0;
        };
    }
}
