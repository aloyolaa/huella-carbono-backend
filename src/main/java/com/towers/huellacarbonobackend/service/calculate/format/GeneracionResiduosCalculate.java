package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.calculate.FCMCondicion;
import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.GeneracionResiduos;
import com.towers.huellacarbonobackend.entity.data.GeneracionResiduosDetalle;
import com.towers.huellacarbonobackend.service.calculate.FCMCondicionService;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeneracionResiduosCalculate {
    private final FCMCondicionService fcmCondicionService;
    private final PCGCombustibleService pcgCombustibleService;

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            GeneracionResiduos generacionResiduos = datosGenerales.getDetalles().get(0).getGeneracionResiduos();
            // Valores Adicionales I del Cálculo
            double pet = 0.21 * (0.46 * generacionResiduos.getTemperatura() + 8);
            int condC = generacionResiduos.getPrecipitacion() / pet > 1 ? 1 : 2;
            int iK = (generacionResiduos.getTemperatura() <= 20 ? 1 : 3) + (generacionResiduos.getPrecipitacion() / pet > 1 ? 0 : 1);
            double[][] kDatos = {
                    {0.03, 0.02, 0.035, 0.025},
                    {0.06, 0.04, 0.07, 0.045},
                    {0.185, 0.06, 0.4, 0.085},
                    {0.06, 0.04, 0.07, 0.045},
                    {0.1, 0.05, 0.17, 0.065},
                    {0.185, 0.06, 0.4, 0.085},
                    {0, 0, 0}
            };

            int[][] docDatos = {
                    {43, 50},
                    {40, 44},
                    {15, 38},
                    {24, 30},
                    {20, 49},
                    {20, 60},
                    {0, 0}
            };

            // Valores Adicionales II del Cálculo
            double[] k = {
                    kDatos[0][iK - 1],
                    kDatos[1][iK - 1],
                    kDatos[2][iK - 1],
                    kDatos[3][iK - 1],
                    kDatos[4][iK - 1],
                    kDatos[5][iK - 1],
                    kDatos[6][iK - 1]
            };

            double[] o = {
                    1 - Math.exp(-1 * k[0]),
                    1 - Math.exp(-1 * k[1]),
                    1 - Math.exp(-1 * k[2]),
                    1 - Math.exp(-1 * k[3]),
                    1 - Math.exp(-1 * k[4]),
                    1 - Math.exp(-1 * k[5]),
                    1 - Math.exp(-1 * k[6])
            };

            double[] doc = {
                    (double) docDatos[0][condC - 1] / 100,
                    (double) docDatos[1][condC - 1] / 100,
                    (double) docDatos[2][condC - 1] / 100,
                    (double) docDatos[3][condC - 1] / 100,
                    (double) docDatos[4][condC - 1] / 100,
                    (double) docDatos[5][condC - 1] / 100,
                    (double) docDatos[6][condC - 1] / 100
            };

            // Valores Propios del Cálculo
            double cord = 0.5;
            double fcl = generacionResiduos.getContenidoGrasas() ? 0.55 : 0.5;
            double rc = (double) 16 / 12;
            int fo = 1 - 0; // La fórmula de Fracción Oxidación es 1 - X, X vale 0 en el formato de la calculadora
            int rm = 0; // No hay fórmula para Reducción Metano, valor por defecto 0
            double ch4 = pcgCombustibleService.getByNombre("Metano - biomasa").getValor();
            FCMCondicion fcmCondicion = fcmCondicionService.getByCondicionSEDS(generacionResiduos.getCondicionSEDS().getId());
            for (GeneracionResiduosDetalle residuosDetalle : generacionResiduos.getGeneracionResiduosDetalles()) {
                double consumo = residuosDetalle.getProductosMadera() + residuosDetalle.getProductosPapel() + residuosDetalle.getResiduos() +
                        residuosDetalle.getTextiles() + residuosDetalle.getJardines() + residuosDetalle.getPaniales() + residuosDetalle.getOtros();

                double fcm = fcmCondicion.getValor();
                int difAnios = generacionResiduos.getAnioHuella() - residuosDetalle.getAnio();

                List<Double> d = List.of(
                        Math.exp(-1 * k[0] * (difAnios)),
                        Math.exp(-1 * k[1] * (difAnios)),
                        Math.exp(-1 * k[2] * (difAnios)),
                        Math.exp(-1 * k[3] * (difAnios)),
                        Math.exp(-1 * k[4] * (difAnios)),
                        Math.exp(-1 * k[5] * (difAnios)),
                        Math.exp(-1 * k[6] * (difAnios))
                );

                double cod = (o[0] * doc[0] * residuosDetalle.getProductosMadera() * d.get(0)) +
                        (o[1] * doc[1] * residuosDetalle.getProductosPapel() * d.get(1)) +
                        (o[2] * doc[2] * residuosDetalle.getResiduos() * d.get(2)) +
                        (o[3] * doc[3] * residuosDetalle.getTextiles() * d.get(3)) +
                        (o[4] * doc[4] * residuosDetalle.getJardines() * d.get(4)) +
                        (o[5] * doc[5] * residuosDetalle.getPaniales() * d.get(5)) +
                        (o[6] * doc[6] * residuosDetalle.getOtros() * d.get(6));

                double pgm = cod * cord * fcl * rc;
                double grm = pgm * fcm;
                double tmg = grm / 1000; // En teoria deberia se grm * consumo;
                double e = (tmg - rm) * fo * ch4;
                total += e;
            }
            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
