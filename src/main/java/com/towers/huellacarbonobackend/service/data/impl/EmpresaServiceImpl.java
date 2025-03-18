package com.towers.huellacarbonobackend.service.data.impl;

import com.towers.huellacarbonobackend.dto.ArchivoDto;
import com.towers.huellacarbonobackend.dto.EmpresaDto;
import com.towers.huellacarbonobackend.entity.data.Empresa;
import com.towers.huellacarbonobackend.entity.data.Usuario;
import com.towers.huellacarbonobackend.exception.DataAccessExceptionImpl;
import com.towers.huellacarbonobackend.mapper.ArchivoMapper;
import com.towers.huellacarbonobackend.repository.EmpresaArchivoRepository;
import com.towers.huellacarbonobackend.repository.EmpresaRepository;
import com.towers.huellacarbonobackend.service.data.EmpresaService;
import com.towers.huellacarbonobackend.service.security.EmailService;
import com.towers.huellacarbonobackend.service.security.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final UsuarioService usuarioService;
    private final EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public Empresa getById(Long id) {
        try {
            return empresaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Empresa con el ID " + id + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional
    public void registrarEmpresa(EmpresaDto empresaDto) {
        if (empresaRepository.existsByRazonSocial(empresaDto.ruc())) {
            throw new DataAccessExceptionImpl("Ya existe una empresa con la Razón Social: " + empresaDto.razonSocial());
        }

        if (empresaRepository.existsByRuc(empresaDto.ruc())) {
            throw new DataAccessExceptionImpl("Ya existe una empresa con el RUC: " + empresaDto.ruc());
        }

        if (empresaRepository.existsByCorreo(empresaDto.correo())) {
            throw new DataAccessExceptionImpl("Ya existe una empresa con el correo: " + empresaDto.correo());
        }

        Empresa empresa = new Empresa();
        empresa.setRazonSocial(empresaDto.razonSocial().trim().toUpperCase());
        empresa.setRuc(empresaDto.ruc().trim());
        empresa.setDireccion(empresaDto.direccion().trim().toUpperCase());
        empresa.setTelefono(empresaDto.telefono());
        empresa.setCorreo(empresaDto.correo().trim().toUpperCase());

        empresaRepository.save(empresa);

        String password = generarPasswordAleatorio();

        Usuario usuario = usuarioService.saveByEmpresa(empresa, password);

        emailService.enviarCorreoRestablecimiento(usuario, password);
    }

    @Override
    public String generarPasswordAleatorio() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
