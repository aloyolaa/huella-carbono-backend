package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import org.springframework.stereotype.Component;

import static com.towers.huellacarbonobackend.service.calculate.format.CommonCalculate.getConsumoMes;

@Component
public class ConsumoAguaCalculate {
    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            for (int mes = 1; mes <= 12; mes++) {
                total += calculateByMes(datosGenerales, mes);
            }

            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public double calculateByMes(DatosGenerales datosGenerales, int mes) {
        try {
            double total = 0;
            for (Detalle detalle : datosGenerales.getDetalles()) {
                double consumoAgua = getConsumoMes(detalle, mes);
                double e = consumoAgua * 0.344 / 1000;
                total += e;
            }

            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
