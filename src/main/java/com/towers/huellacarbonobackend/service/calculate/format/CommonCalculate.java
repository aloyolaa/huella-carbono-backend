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

    public static double getConsumoMes(Detalle detalle, int mes) {
        return switch (mes) {
            case 1 -> detalle.getMeses().getEnero() != null ? detalle.getMeses().getEnero() : 0;
            case 2 -> detalle.getMeses().getFebrero() != null ? detalle.getMeses().getFebrero() : 0;
            case 3 -> detalle.getMeses().getMarzo() != null ? detalle.getMeses().getMarzo() : 0;
            case 4 -> detalle.getMeses().getAbril() != null ? detalle.getMeses().getAbril() : 0;
            case 5 -> detalle.getMeses().getMayo() != null ? detalle.getMeses().getMayo() : 0;
            case 6 -> detalle.getMeses().getJunio() != null ? detalle.getMeses().getJunio() : 0;
            case 7 -> detalle.getMeses().getJulio() != null ? detalle.getMeses().getJulio() : 0;
            case 8 -> detalle.getMeses().getAgosto() != null ? detalle.getMeses().getAgosto() : 0;
            case 9 -> detalle.getMeses().getSeptiembre() != null ? detalle.getMeses().getSeptiembre() : 0;
            case 10 -> detalle.getMeses().getOctubre() != null ? detalle.getMeses().getOctubre() : 0;
            case 11 -> detalle.getMeses().getNoviembre() != null ? detalle.getMeses().getNoviembre() : 0;
            case 12 -> detalle.getMeses().getDiciembre() != null ? detalle.getMeses().getDiciembre() : 0;
            default -> 0;
        };
    }
}
