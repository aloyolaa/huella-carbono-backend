package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tipo_combustible")
public class TipoCombustible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unidad_id", nullable = false)
    private Unidad unidad;

    @ManyToOne
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "archivo_id", nullable = false)
    private Archivo archivo;

    public TipoCombustible(Long id) {
        this.id = id;
    }
}