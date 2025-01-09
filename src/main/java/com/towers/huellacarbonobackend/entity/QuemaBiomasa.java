package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quema_biomasa")
@AllArgsConstructor
@NoArgsConstructor
public class QuemaBiomasa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "residuo_agricola_id", nullable = false)
    private ResiduoAgricola residuoAgricola;

    @Column(name = "area_cultiva", nullable = false)
    private Double areaCultiva;

    @Column(name = "area_quemada", nullable = false)
    private Double areaQuemada;

    @Column(name = "produccion", nullable = false)
    private Double produccion;
}