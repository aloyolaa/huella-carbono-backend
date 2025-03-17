package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import org.springframework.stereotype.Component;

import static com.towers.huellacarbonobackend.service.calculate.format.CommonCalculate.getConsumo;

@Component
public class ConsumoAguaCalculate {
    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            for (Detalle detalle : datosGenerales.getDetalles()) {
                double consumoAgua = getConsumo(detalle);
                double e = consumoAgua * 0.344 / 1000;
                total += e;
            }

            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
