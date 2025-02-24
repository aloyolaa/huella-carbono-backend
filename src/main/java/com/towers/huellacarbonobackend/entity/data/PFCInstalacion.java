package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pfc_instalacion")
@NoArgsConstructor
public class PFCInstalacion extends PFC {
    @Column(name = "fuga_instalacion", nullable = false)
    private Double fugaInstalacion;

    public PFCInstalacion(Long id, String descripcionEquipo, TipoPFC tipoPFC, Integer numeroEquipos, Double capacidadCarga, Double fugaInstalacion) {
        super(id, descripcionEquipo, tipoPFC, numeroEquipos, capacidadCarga);
        this.fugaInstalacion = fugaInstalacion;
    }
}