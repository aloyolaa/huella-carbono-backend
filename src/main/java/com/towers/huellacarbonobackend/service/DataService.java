package com.towers.huellacarbonobackend.service;

import com.towers.huellacarbonobackend.dto.DataDto;

public interface DataService {
    void save(DataDto dataDto, Long empresa, Long archivo);
}
