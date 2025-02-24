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
@Table(name = "refrigerante_disposicion")
@NoArgsConstructor
public class RefrigeranteDisposicion extends Refrigerante {
    @Column(name = "fraccion_refrigerante_disposicion", nullable = false)
    private Double fraccionRefrigeranteDisposicion;

    @Column(name = "fraccion_refrigerante_recuperado", nullable = false)
    private Double fraccionRefrigeranteRecuperado;

    public RefrigeranteDisposicion(Long id, TipoEquipo tipoEquipo, TipoRefrigerante tipoRefrigerante, Integer numeroEquipos, Double capacidadCarga, Double fraccionRefrigeranteDisposicion, Double fraccionRefrigeranteRecuperado) {
        super(id, tipoEquipo, tipoRefrigerante, numeroEquipos, capacidadCarga);
        this.fraccionRefrigeranteDisposicion = fraccionRefrigeranteDisposicion;
        this.fraccionRefrigeranteRecuperado = fraccionRefrigeranteRecuperado;
    }
}