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
                List<Detalle> detalles = collect.get(transporte);
                int personas = detalles.stream()
                        .mapToInt(d -> d.getTransporteVehiculo().getPersonasViajando()).sum();
                double distancia = detalles.stream()
                        .mapToDouble(d -> d.getTransporteVehiculo().getDistanciaRecorrida() * d.getTransporteVehiculo().getVecesRecorrido()).sum();
                double recorrido = personas * distancia;
                double co2 = recorrido * fe.getCo2();
                double ch4 = recorrido * fe.getCh4();
                double n2o = recorrido * fe.getN2o();
                double e = ((co2 * pcgCO2) + (ch4 * pcgCH4) + (n2o * pcgN2O)) / 1000;
                total += e;
            }
            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
