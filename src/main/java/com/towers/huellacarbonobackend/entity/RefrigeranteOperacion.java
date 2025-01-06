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
@Table(name = "refrigerante_operacion")
@NoArgsConstructor
public class RefrigeranteOperacion extends Refrigerante {
    @Column(name = "anio", nullable = false)
    private Double anio;

    @Column(name = "fuga_uso", nullable = false)
    private Double fugaUso;

    public RefrigeranteOperacion(Long id, TipoEquipo tipoEquipo, TipoRefrigerante tipoRefrigerante, Double numeroEquipos, Double capacidadCarga, Double anio, Double fugaUso) {
        super(id, tipoEquipo, tipoRefrigerante, numeroEquipos, capacidadCarga);
        this.anio = anio;
        this.fugaUso = fugaUso;
    }
}