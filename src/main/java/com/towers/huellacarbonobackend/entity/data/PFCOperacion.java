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
@Table(name = "pfc_operacion")
@NoArgsConstructor
public class PFCOperacion extends PFC {
    @Column(name = "tiempo_uso", nullable = false)
    private Double tiempoUso;

    @Column(name = "fuga_uso", nullable = false)
    private Double fugaUso;

    public PFCOperacion(Long id, String descripcionEquipo, TipoPFC tipoPFC, Integer numeroEquipos, Double capacidadCarga, Double tiempoUso, Double fugaUso) {
        super(id, descripcionEquipo, tipoPFC, numeroEquipos, capacidadCarga);
        this.tiempoUso = tiempoUso;
        this.fugaUso = fugaUso;
    }
}