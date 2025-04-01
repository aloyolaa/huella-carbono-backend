package com.towers.huellacarbonobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsDataDto {
    private Integer time;
    private Double value;
    private String valueStr;
}
