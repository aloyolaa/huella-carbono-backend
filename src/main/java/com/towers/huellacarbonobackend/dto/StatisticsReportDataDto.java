package com.towers.huellacarbonobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsReportDataDto {
    private String alcance1Mes;
    private Double alcance1Valor;
    private String alcance2Mes;
    private Double alcance2Valor;
    private String alcance3Mes;
    private Double alcance3Valor;
}
