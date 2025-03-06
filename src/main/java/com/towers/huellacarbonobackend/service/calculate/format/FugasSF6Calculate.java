package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.*;
import org.springframework.stereotype.Component;

@Component
public class FugasSF6Calculate {
    public double calculate(DatosGenerales datosGenerales) {
        double total = 0;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            FugaInstalacion fI = detalle.getFugaInstalacion();
            FugaOperacion fO = detalle.getFugaOperacion();
            FugaDisposicion fD = detalle.getFugaDisposicion();
            double i = fI != null ? fI.getNumeroEquipos() * fI.getCapacidadCarga() * fI.getFugaInstalacion() : 0.0;
            double o = fO != null ? fO.getNumeroEquipos() * fO.getCapacidadCarga() * fO.getTiempoUso() * fO.getFugaUso() : 0.0;
            double d = fD != null ? fD.getNumeroEquipos() * fD.getCapacidadCarga() * fD.getFraccionSF6Disposicion() * fD.getFraccionSF6Recuperado() : 0.0;
            double perdidaTotal = (i + o + d) / 1000;
            double e = perdidaTotal * 23500;
            total += e;
        }

        return total;
    }
}
