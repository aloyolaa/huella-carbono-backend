package com.towers.huellacarbonobackend.mapper;

import com.towers.huellacarbonobackend.dto.TipoCombustibleDto;
import com.towers.huellacarbonobackend.entity.TipoCombustible;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoCombustibleMapper {

    public TipoCombustibleDto toTipoCombustibleDto(TipoCombustible tipoCombustible) {
        return new TipoCombustibleDto(
                tipoCombustible.getId(),
                tipoCombustible.getNombre(),
                tipoCombustible.getUnidad().getNombre()
        );
    }
}
