package com.towers.huellacarbonobackend.service.calculate.format;

import com.towers.huellacarbonobackend.entity.data.Detalle;

public class CommonCalculate {
    public static double getConsumo(Detalle detalle) {
        double consumoElectricidad = 0;
        if (detalle.getMeses().getEnero() != null) consumoElectricidad += detalle.getMeses().getEnero();
        if (detalle.getMeses().getFebrero() != null) consumoElectricidad += detalle.getMeses().getFebrero();
        if (detalle.getMeses().getMarzo() != null) consumoElectricidad += detalle.getMeses().getMarzo();
        if (detalle.getMeses().getAbril() != null) consumoElectricidad += detalle.getMeses().getAbril();
        if (detalle.getMeses().getMayo() != null) consumoElectricidad += detalle.getMeses().getMayo();
        if (detalle.getMeses().getJunio() != null) consumoElectricidad += detalle.getMeses().getJunio();
        if (detalle.getMeses().getJulio() != null) consumoElectricidad += detalle.getMeses().getJulio();
        if (detalle.getMeses().getAgosto() != null) consumoElectricidad += detalle.getMeses().getAgosto();
        if (detalle.getMeses().getSeptiembre() != null) consumoElectricidad += detalle.getMeses().getSeptiembre();
        if (detalle.getMeses().getOctubre() != null) consumoElectricidad += detalle.getMeses().getOctubre();
        if (detalle.getMeses().getNoviembre() != null) consumoElectricidad += detalle.getMeses().getNoviembre();
        if (detalle.getMeses().getDiciembre() != null) consumoElectricidad += detalle.getMeses().getDiciembre();
        return consumoElectricidad;
    }
}
