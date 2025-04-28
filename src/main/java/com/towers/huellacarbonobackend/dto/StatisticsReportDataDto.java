package com.towers.huellacarbonobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsReportDataDto {
    private String a1Mes;
    private String a2Mes;
    private String a3Mes;
    private Double a1Valor;
    private Double a2Valor;
    private Double a3Valor;
}
