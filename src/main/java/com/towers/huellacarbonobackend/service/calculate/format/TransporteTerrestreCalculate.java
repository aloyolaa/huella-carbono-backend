package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FEVehiculo;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.calculate.FEVehiculoService;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransporteTerrestreCalculate {
    private final FEVehiculoService feVehiculoService;
    private final PCGCombustibleService pcgCombustibleService;

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            double pcgCO2 = pcgCombustibleService.getByNombre("Dióxido de carbono").getValor();
            double pcgCH4 = pcgCombustibleService.getByNombre("Metano - fósil").getValor();
            double pcgN2O = pcgCombustibleService.getByNombre("Óxido nitroso").getValor();
            Map<Long, List<Detalle>> collect = datosGenerales.getDetalles().stream()
                    .collect(Collectors.groupingBy(detalle -> detalle.getTransporteVehiculo().getTipoTransporte().getId()));
            for (Long transporte : collect.keySet()) {
                FEVehiculo fe = feVehiculoService.getByTipoTransporte(transporte);
                double subtotal = 0;
                List<Detalle> detalles = collect.get(transporte);
                for (Detalle detalle : detalles) {
                    double recorrido = detalle.getTransporteVehiculo().getPersonasViajando() * detalle.getTransporteVehiculo().getDistanciaRecorrida() * detalle.getTransporteVehiculo().getVecesRecorrido();
                    double co2 = recorrido * fe.getCo2();
                    double ch4 = recorrido * fe.getCh4();
                    double n2o = recorrido * fe.getN2o();
                    double e = ((co2 * pcgCO2) + (ch4 * pcgCH4) + (n2o * pcgN2O)) / 1000;
                    subtotal += e;
                }
                total += subtotal;
            }
            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
