package com.towers.huellacarbonobackend.entity.data;

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
public class Fuga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "descripcion_equipo", nullable = false)
    private String descripcionEquipo;

    @Column(name = "numero_equipos", nullable = false)
    private Integer numeroEquipos;

    @Column(name = "capacidad_carga", nullable = false)
    private Double capacidadCarga;
}