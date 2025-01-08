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
@Table(name = "fuga_instalacion")
@NoArgsConstructor
public class FugaInstalacion extends Fuga {
    @Column(name = "fuga_instalacion", nullable = false)
    private Double fugaInstalacion;

    public FugaInstalacion(Long id, String descripcionEquipo, Integer numeroEquipos, Double capacidadCarga, Double fugaInstalacion) {
        super(id, descripcionEquipo, numeroEquipos, capacidadCarga);
        this.fugaInstalacion = fugaInstalacion;
    }
}