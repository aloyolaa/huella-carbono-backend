package com.towers.huellacarbonobackend.service.calculate.impl;

import com.towers.huellacarbonobackend.entity.calculate.PCGPFC;
import com.towers.huellacarbonobackend.repository.PCGPFCRepository;
import com.towers.huellacarbonobackend.service.calculate.PCGPFCService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PCGPFCServiceImpl implements PCGPFCService {
    private final PCGPFCRepository pcgpfcRepository;

    @Override
    @Transactional(readOnly = true)
    public PCGPFC getByTipoPFC(Long id) {
        return pcgpfcRepository.findByTipoPFC(id)
                .orElse(null);
    }
}
