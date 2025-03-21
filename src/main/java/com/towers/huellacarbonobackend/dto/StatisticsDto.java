package com.towers.huellacarbonobackend.dto;

import java.util.List;

public record StatisticsDto(
        List<StatisticsDataDto> alcance1,
        List<StatisticsDataDto> alcance2,
        List<StatisticsDataDto> alcance3
) {
}
