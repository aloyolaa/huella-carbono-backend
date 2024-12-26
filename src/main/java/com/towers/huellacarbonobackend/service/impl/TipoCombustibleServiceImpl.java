package com.towers.huellacarbonobackend.service.impl;

import com.towers.huellacarbonobackend.entity.TipoCombustible;
import com.towers.huellacarbonobackend.repository.TipoCombustibleRepository;
import com.towers.huellacarbonobackend.service.TipoCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCombustibleServiceImpl implements TipoCombustibleService {
    private final TipoCombustibleRepository tipoCombustibleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoCombustible> getAll() {
        return tipoCombustibleRepository.findAll();
    }
}
