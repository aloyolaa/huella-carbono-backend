package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clinker")
@AllArgsConstructor
@NoArgsConstructor
public class Clinker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cemento", nullable = false)
    private String cemento;

    @Column(name = "produccion_cemento", nullable = false)
    private Double produccionCemento;

    @Column(name = "produccion_clinker", nullable = false)
    private Double produccionClinker;

    @Column(name = "contenido_cao_clinker", nullable = false)
    private Double contenidoCaOClinker;

    @Column(name = "contenido_cao_caco3", nullable = false)
    private Double contenidoCaOCaCO3;
}