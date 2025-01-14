package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.entity.CondicionSEDS;
import com.towers.huellacarbonobackend.repository.CondicionSEDSRepository;
import com.towers.huellacarbonobackend.service.data.CondicionSEDSService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CondicionSEDSServiceImpl implements CondicionSEDSService {
    private final CondicionSEDSRepository condicionSEDSRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CondicionSEDS> getAll() {
        return condicionSEDSRepository.findAll();
    }
}
