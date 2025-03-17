package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FEMovilidad;
import com.towers.huellacarbonobackend.entity.data.*;
import com.towers.huellacarbonobackend.service.calculate.FEMovilidadService;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransporteCasaTrabajoCalculate {
    private final FEMovilidadService feMovilidadService;
    private final PCGCombustibleService pcgCombustibleService;

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            double pcgCO2 = pcgCombustibleService.getByNombre("Dióxido de carbono").getValor();
            double pcgCH4 = pcgCombustibleService.getByNombre("Metano - fósil").getValor();
            double pcgN2O = pcgCombustibleService.getByNombre("Óxido nitroso").getValor();
            Map<Long, List<TransporteCasaTrabajo>> collect = datosGenerales.getDetalles().stream()
                    .flatMap(detalle -> detalle.getTransporteCasaTrabajos().stream())
                    .collect(Collectors.groupingBy(t -> t.getTipoMovilidad().getId()));

            for (Long movilidad : collect.keySet()) {
                FEMovilidad fe = feMovilidadService.getByTipoMovilidad(movilidad);
                List<TransporteCasaTrabajo> list = collect.get(movilidad);
                double recorrido = list.stream()
                        .mapToDouble(t -> t.getViajesSemanales() * t.getDiasLaborales() * t.getDistanciaViaje() * getFactor(t.getDiasLaborales()) * t.getTrabajadores())
                        .sum();
                double co2 = recorrido * fe.getCo2();
                double ch4 = recorrido * fe.getCh4();
                double n2o = recorrido * fe.getN2o();

                double e = (co2 * pcgCO2) + (ch4 * pcgCH4) + (n2o * pcgN2O);
                total += e;
            }

            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    private double getFactor(int diasLaborales) {
        double v;
        if (diasLaborales < 260) {
            v = (double) 1 / 5;
        } else if (diasLaborales < 312) {
            v = (double) 1 / 6;
        } else {
            v = (double) 1 / 7;
        }
        return v;
    }
}
