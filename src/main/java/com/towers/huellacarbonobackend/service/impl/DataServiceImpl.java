package com.towers.huellacarbonobackend.service.impl;

import com.towers.huellacarbonobackend.dto.DataDto;
import com.towers.huellacarbonobackend.mapper.DataMapper;
import com.towers.huellacarbonobackend.repository.DatosGeneralesRepository;
import com.towers.huellacarbonobackend.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DatosGeneralesRepository datosGeneralesRepository;
    private final DataMapper dataMapper;

    @Override
    public void save(DataDto dataDto, Long empresa, Long archivo) {
        datosGeneralesRepository.save(dataMapper.toDatosGenerales(dataDto, empresa, archivo));
    }
}
