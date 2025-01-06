package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "actividad")
@NoArgsConstructor
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produccion_id", nullable = false)
    private Produccion produccion;

    @ManyToOne
    @JoinColumn(name = "accion_id")
    private Accion accion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "archivo_id", nullable = false)
    private Archivo archivo;

    public Actividad(Long id) {
        this.id = id;
    }
}