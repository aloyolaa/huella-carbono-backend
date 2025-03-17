package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FPapel;
import com.towers.huellacarbonobackend.entity.data.ConsumoPapel;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.service.calculate.FPapelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumoPapelCalculate {
    private final FPapelService fPapelService;

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            for (Detalle detalle : datosGenerales.getDetalles()) {
                ConsumoPapel consumoPapel = detalle.getConsumoPapel();
                FPapel f = fPapelService.getByTipoHoja(consumoPapel.getTipoHoja().getId());
                double cantidad = consumoPapel.getComprasAnuales() * consumoPapel.getDensidad() * f.getFactorA() * f.getFactorB() / 10000;
                double e = (cantidad * 0.7954032 / 1000 * consumoPapel.getReciclado()) + (cantidad * 0.9556535 / 1000 * (1 - consumoPapel.getReciclado()));
                total += e;
            }
            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
