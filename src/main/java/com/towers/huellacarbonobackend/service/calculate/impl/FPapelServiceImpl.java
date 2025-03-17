package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FPapel;
import com.towers.huellacarbonobackend.repository.FPapelRepository;
import com.towers.huellacarbonobackend.service.calculate.FPapelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FPapelServiceImpl implements FPapelService {
    private final FPapelRepository fPapelRepository;

    @Override
    @Transactional(readOnly = true)
    public FPapel getByTipoHoja(Long id) {
        return fPapelRepository.findByTipoHoja(id)
                .orElse(null);
    }
}
