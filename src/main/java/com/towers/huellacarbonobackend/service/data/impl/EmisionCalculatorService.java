package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.exception.DataServiceException;
import com.towers.huellacarbonobackend.service.calculate.format.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmisionCalculatorService {
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

    public double calculateEmision(DatosGenerales datosGenerales) {
        if (datosGenerales == null || datosGenerales.getArchivo() == null || datosGenerales.getArchivo().getId() == null) {
            log.warn("DatosGenerales or Archivo is null, returning 0.0 emission");
            return 0.0;
        }

        Long archivoId = datosGenerales.getArchivo().getId();

        try {
            return switch (archivoId.intValue()) {
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
                default -> {
                    log.warn("Unknown archivo ID: {}, returning 0.0 emission", archivoId);
                    yield 0.0;
                }
            };
        } catch (Exception e) {
            log.error("Error calculating emission for archivo ID: {}", archivoId, e);
            throw new DataServiceException("Error al calcular emisión para archivo ID: " + archivoId, e);
        }
    }
}
