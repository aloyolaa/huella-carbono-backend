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
@Table(name = "refrigerante_instalacion")
@NoArgsConstructor
public class RefrigeranteInstalacion extends Refrigerante {
    @Column(name = "fuga_instalacion", nullable = false)
    private Double fugaInstalacion;

    public RefrigeranteInstalacion(Long id, TipoEquipo tipoEquipo, TipoRefrigerante tipoRefrigerante, Integer numeroEquipos, Double capacidadCarga, Double fugaInstalacion) {
        super(id, tipoEquipo, tipoRefrigerante, numeroEquipos, capacidadCarga);
        this.fugaInstalacion = fugaInstalacion;
    }
}