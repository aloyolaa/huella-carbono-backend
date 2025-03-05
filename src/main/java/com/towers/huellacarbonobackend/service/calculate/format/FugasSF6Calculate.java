package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FugasSF6Calculate {
    public double calculate(DatosGenerales datosGenerales) {
        double total = 0;
        for (Detalle detalle : datosGenerales.getDetalles()) {
            FugaInstalacion fugaInstalacion = detalle.getFugaInstalacion();
            FugaOperacion fugaOperacion = detalle.getFugaOperacion();
            FugaDisposicion fugaDisposicion = detalle.getFugaDisposicion();
            double instalacion = fugaInstalacion != null ? fugaInstalacion.getNumeroEquipos() * fugaInstalacion.getCapacidadCarga() * fugaInstalacion.getFugaInstalacion() : 0.0;
            double operacion = fugaOperacion != null ? fugaOperacion.getNumeroEquipos() * fugaOperacion.getCapacidadCarga() * fugaOperacion.getTiempoUso() * fugaOperacion.getFugaUso() : 0.0;
            double disposicion = fugaDisposicion != null ? fugaDisposicion.getNumeroEquipos() * fugaDisposicion.getCapacidadCarga() * fugaDisposicion.getFraccionSF6Disposicion() * fugaDisposicion.getFraccionSF6Recuperado() : 0.0;
            double perdidaTotal = (instalacion + operacion + disposicion) / 1000;
            double emision = perdidaTotal * 23500;
            total += emision;
        }

        return total;
    }
}
