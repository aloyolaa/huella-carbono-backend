package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FugasSF6Calculate {
    public double calculate(DatosGenerales datosGenerales) {
        List<Detalle> detalles = datosGenerales.getDetalles();
        List<FugaInstalacion> dataI = detalles.stream().map(Detalle::getFugaInstalacion).toList();
        List<FugaOperacion> dataO = detalles.stream().map(Detalle::getFugaOperacion).toList();
        List<FugaDisposicion> dataD = detalles.stream().map(Detalle::getFugaDisposicion).toList();

        double i = dataI.stream().mapToDouble(fI -> fI != null ? fI.getNumeroEquipos() * fI.getCapacidadCarga() * fI.getFugaInstalacion() : 0.0).sum();
        double o = dataO.stream().mapToDouble(fO -> fO != null ? fO.getNumeroEquipos() * fO.getCapacidadCarga() * fO.getTiempoUso() * fO.getFugaUso() : 0.0).sum();
        double d = dataD.stream().mapToDouble(fD -> fD != null ? fD.getNumeroEquipos() * fD.getCapacidadCarga() * fD.getFraccionSF6Disposicion() * fD.getFraccionSF6Recuperado() : 0.0).sum();
        double perdidaTotal = (i + o + d) / 1000;

        return perdidaTotal * 23500;
    }
}
