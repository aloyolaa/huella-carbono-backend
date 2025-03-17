package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FEMaterial;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.calculate.FEMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransporteMaterialCalculate {
    private final FEMaterialService feMaterialService;

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            Map<Long, List<Detalle>> collect = datosGenerales.getDetalles().stream()
                    .collect(Collectors.groupingBy(detalle -> detalle.getTransporteMaterial().getTipoVehiculo().getId()));
            for (Long vehiculo : collect.keySet()) {
                FEMaterial fe = feMaterialService.getByTipoVehiculo(vehiculo);
                double subtotal = 0;
                List<Detalle> detalles = collect.get(vehiculo);
                for (Detalle detalle : detalles) {
                    double recorrido = detalle.getTransporteMaterial().getPesoTransportado() * detalle.getTransporteMaterial().getDistanciaRecorrida() * detalle.getTransporteMaterial().getViajes() / 1000;
                    double e = recorrido * fe.getValor() / 1000;
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
