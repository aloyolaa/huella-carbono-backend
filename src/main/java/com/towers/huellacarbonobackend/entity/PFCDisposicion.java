package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pfc_disposicion")
@NoArgsConstructor
public class PFCDisposicion extends PFC {
    @Column(name = "fraccion_gas_pfc_disposicion", nullable = false)
    private Double fraccionGasPFCDisposicion;

    @Column(name = "fraccion_gas_pfc_recuperado", nullable = false)
    private Double fraccionGasPFCRecuperado;

    public PFCDisposicion(Long id, String descripcionEquipo, TipoPFC tipoPFC, Integer numeroEquipos, Double capacidadCarga, Double fraccionGasPFCDisposicion, Double fraccionGasPFCRecuperado) {
        super(id, descripcionEquipo, tipoPFC, numeroEquipos, capacidadCarga);
        this.fraccionGasPFCDisposicion = fraccionGasPFCDisposicion;
        this.fraccionGasPFCRecuperado = fraccionGasPFCRecuperado;
    }
}