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
public class Refrigerante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_equipo_id", nullable = false)
    private TipoEquipo tipoEquipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_refrigerante_id", nullable = false)
    private TipoRefrigerante tipoRefrigerante;

    @Column(name = "numero_equipos", nullable = false)
    private Double numeroEquipos;

    @Column(name = "capacidad_carga", nullable = false)
    private Double capacidadCarga;
}