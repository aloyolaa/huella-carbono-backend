package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FactorEmisionConsumo;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class TransporteAereoCalculate {
    public double calculate(DatosGenerales datosGenerales) {
        try {
            List<Detalle> grupoA = filtrarDetalles(datosGenerales, d -> d.getTransporteVehiculo().getDistanciaRecorrida() <= 1600.0);
            List<Detalle> grupoB = filtrarDetalles(datosGenerales, d -> d.getTransporteVehiculo().getDistanciaRecorrida() > 1600.0 && d.getTransporteVehiculo().getDistanciaRecorrida() < 3700);
            List<Detalle> grupoC = filtrarDetalles(datosGenerales, d -> d.getTransporteVehiculo().getDistanciaRecorrida() >= 3700.0);
            double eA = calculateByGrupo(grupoA, 0.29832);
            double eB = calculateByGrupo(grupoB, 0.16236);
            double eC = calculateByGrupo(grupoC, 0.21256);

            return eA + eB + eC;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    private List<Detalle> filtrarDetalles(DatosGenerales datosGenerales, Predicate<Detalle> predicate) {
        return datosGenerales.getDetalles().stream()
                .filter(predicate).toList();
    }

    private double calculateByGrupo(List<Detalle> detalle, double fe) {
        int personas = detalle.stream()
                .mapToInt(d -> d.getTransporteVehiculo().getPersonasViajando()).sum();
        double distancia = detalle.stream()
                .mapToDouble(d -> d.getTransporteVehiculo().getDistanciaRecorrida() * d.getTransporteVehiculo().getVecesRecorrido()).sum();
        double recorrido = personas * distancia;
        double e = fe * recorrido;
        return e / 1000;
    }
}
