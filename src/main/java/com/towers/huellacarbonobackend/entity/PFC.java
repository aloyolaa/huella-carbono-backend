package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class PFC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "descripcion_equipo", nullable = false)
    private String descripcionEquipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_pfc_id", nullable = false)
    private TipoPFC tipoPFC;

    @Column(name = "numero_equipos", nullable = false)
    private Integer numeroEquipos;

    @Column(name = "capacidad_carga", nullable = false)
    private Double capacidadCarga;
}