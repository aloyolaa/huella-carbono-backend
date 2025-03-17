package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.DatosGenerales;
import com.towers.huellacarbonobackend.entity.data.Detalle;
import com.towers.huellacarbonobackend.entity.data.GeneracionIndirectaNF3;
import com.towers.huellacarbonobackend.service.calculate.PCGCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneracionIndirectaNF3Calculate {
    private final PCGCombustibleService pcgCombustibleService;

    public double calculate(DatosGenerales datosGenerales) {
        try {
            double total = 0;
            double pcgNF3 = pcgCombustibleService.getByNombre("Trifluoruro de nitrógeno").getValor();
            for (Detalle detalle : datosGenerales.getDetalles()) {
                GeneracionIndirectaNF3 nf3 = detalle.getGeneracionIndirectaNF3();
                int pantallas = nf3.getNumeroPantallas();
                double areaIn = nf3.getAlto() * nf3.getAncho();
                double areaM2 = areaIn / 1550;
                double e = (pantallas * areaM2 * 0.9) / 1000000;
                double gei = e * pcgNF3 / 1000;
                total += gei;
            }

            return total;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
