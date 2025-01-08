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
@Table(name = "fuga_disposicion")
@NoArgsConstructor
public class FugaDisposicion extends Fuga {
    @Column(name = "fraccion_sf_6_disposicion", nullable = false)
    private Double fraccionSF6Disposicion;

    @Column(name = "fraccion_sf_6_recuperado", nullable = false)
    private Double fraccionSF6Recuperado;

    public FugaDisposicion(Long id, String descripcionEquipo, Integer numeroEquipos, Double capacidadCarga, Double fraccionSF6Disposicion, Double fraccionSF6Recuperado) {
        super(id, descripcionEquipo, numeroEquipos, capacidadCarga);
        this.fraccionSF6Disposicion = fraccionSF6Disposicion;
        this.fraccionSF6Recuperado = fraccionSF6Recuperado;
    }
}