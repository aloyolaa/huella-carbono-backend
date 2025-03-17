package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.FCMCondicion;
import com.towers.huellacarbonobackend.repository.FCMCondicionRepository;
import com.towers.huellacarbonobackend.service.calculate.FCMCondicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FCMCondicionServiceImpl implements FCMCondicionService {
    private final FCMCondicionRepository fcmCondicionRepository;

    @Override
    @Transactional(readOnly = true)
    public FCMCondicion getByCondicionSEDS(Long id) {
        return fcmCondicionRepository.findByCondicionSEDS(id)
                .orElse(null);
    }
}
