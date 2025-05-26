package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.mapper.DataMapper;
import com.towers.huellacarbonobackend.repository.DatosGeneralesRepository;
import com.towers.huellacarbonobackend.service.calculate.format.*;
import com.towers.huellacarbonobackend.service.data.DataService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
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
    private final TransporteMaterialCalculate transporteMaterialCalculate;
    private final TransporteAereoCalculate transporteAereoCalculate;
    private final TransporteTerrestreCalculate transporteTerrestreCalculate;
    private final TransporteCasaTrabajoCalculate transporteCasaTrabajoCalculate;
    private final ConsumoAguaCalculate consumoAguaCalculate;
    private final ConsumoPapelCalculate consumoPapelCalculate;
    private final GeneracionIndirectaNF3Calculate generacionIndirectaNF3Calculate;
    private final GeneracionResiduosCalculate generacionResiduosCalculate;

    @PersistenceContext
    private EntityManager entityManager;

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

        if (datosGenerales.getId() != null) {
            Optional<DatosGenerales> existingDatosGeneralesOpt = datosGeneralesRepository.findById(datosGenerales.getId());
            if (existingDatosGeneralesOpt.isPresent()) {
                DatosGenerales existingDatosGenerales = existingDatosGeneralesOpt.orElseThrow();

                for (Detalle detalle : datosGenerales.getDetalles()) {
                    if (detalle.getId() != null) {
                        Detalle finalDetalle = detalle;
                        existingDatosGenerales.getDetalles().removeIf(d -> d.equals(finalDetalle));
                        detalle = entityManager.merge(detalle);
                        existingDatosGenerales.getDetalles().add(detalle);
                    } else {
                        existingDatosGenerales.getDetalles().add(detalle);
                    }
                }

                datosGenerales.setDetalles(existingDatosGenerales.getDetalles());
            }
        }

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
    public Optional<DatosGenerales> getOptionalByEmpresaAndAnio(Long empresaId, Long archivo, Integer anio, Integer mes) {
        return datosGeneralesRepository.findByEmpresaAndAnio(anio, mes, empresaId, archivo);
    }

    @Override
    @Transactional(readOnly = true)
    public DataDto getByEmpresaAndAnio(Long empresaId, Long archivo, Integer anio, Integer mes) {
        return getOptionalByEmpresaAndAnio(empresaId, archivo, anio, mes)
                .map(dataMapper::toDataDto)
                .orElseThrow(() -> new EntityNotFoundException("No hay datos registrados para el año " + anio + " del mes " + mes));
    }

    @Override
    @Transactional(readOnly = true)
    public DatosGenerales getByArchivoAndAnio(Long empresa, Long archivo, Integer anio, Integer mes) {
        return datosGeneralesRepository.findByEmpresaAndAnio(anio, mes, empresa, archivo)
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
            case 3 -> fuentesMovilesYRefinacionCalculate.calculate(datosGenerales);
            case 8 -> refrigerantesCalculate.calculate(datosGenerales);
            case 9 -> fugasSF6Calculate.calculate(datosGenerales);
            case 10 -> pfcCalculate.calculate(datosGenerales);
            case 18 -> consumoElectricidadCalculate.calculate(datosGenerales);
            case 19, 20 -> perdidasCalculate.calculate(datosGenerales);
            case 22, 23 -> transporteMaterialCalculate.calculate(datosGenerales);
            case 24 -> transporteAereoCalculate.calculate(datosGenerales);
            case 25 -> transporteTerrestreCalculate.calculate(datosGenerales);
            case 26 -> transporteCasaTrabajoCalculate.calculate(datosGenerales);
            case 27 -> consumoAguaCalculate.calculate(datosGenerales);
            case 28 -> consumoPapelCalculate.calculate(datosGenerales);
            case 29 -> generacionIndirectaNF3Calculate.calculate(datosGenerales);
            case 30 -> generacionResiduosCalculate.calculate(datosGenerales);
            default -> 0.0;
        };
    }
}
