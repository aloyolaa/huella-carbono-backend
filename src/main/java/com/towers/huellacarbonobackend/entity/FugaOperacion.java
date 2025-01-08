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
@Table(name = "fuga_operacion")
@NoArgsConstructor
public class FugaOperacion extends Fuga {
    @Column(name = "tiempo_uso", nullable = false)
    private Double tiempoUso;

    @Column(name = "fuga_uso", nullable = false)
    private Double fugaUso;

    public FugaOperacion(Long id, String descripcionEquipo, Integer numeroEquipos, Double capacidadCarga, Double tiempoUso, Double fugaUso) {
        super(id, descripcionEquipo, numeroEquipos, capacidadCarga);
        this.tiempoUso = tiempoUso;
        this.fugaUso = fugaUso;
    }
}