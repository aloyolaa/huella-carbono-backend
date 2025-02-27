package com.towers.huellacarbonobackend.service.data;

import com.towers.huellacarbonobackend.dto.EmpresaDto;
import com.towers.huellacarbonobackend.entity.data.Empresa;

public interface EmpresaService {
    Empresa getById(Long id);

    void registrarEmpresa(EmpresaDto empresaDto);

    String generarPasswordAleatorio();
}
