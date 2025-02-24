package com.towers.huellacarbonobackend.entity.calculate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "potencial_calentamiento_global")
public class PotencialCalentamientoGlobal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "formula", nullable = false)
    private String formula;

    @Column(name = "valor", nullable = false)
    private Double valor;
}