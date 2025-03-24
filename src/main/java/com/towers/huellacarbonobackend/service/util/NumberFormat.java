package com.towers.huellacarbonobackend.service.util;

import java.text.DecimalFormat;

public class NumberFormat {
    public static String formatNumber(double number) {
        DecimalFormat df = new DecimalFormat("#,###.##");
        return df.format(number);
    }
}
