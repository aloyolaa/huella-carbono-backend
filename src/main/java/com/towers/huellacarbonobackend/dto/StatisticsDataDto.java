package com.towers.huellacarbonobackend.dto;

public record StatisticsDataDto(
        Integer time,
        Double value,
        String valueStr
) {
}
