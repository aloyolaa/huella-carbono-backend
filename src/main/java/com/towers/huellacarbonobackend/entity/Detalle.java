package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "detalle")
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "datos_generales_id")
    private DatosGenerales datosGenerales;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Meses meses;

    @ManyToOne
    @JoinColumn(name = "tipo_combustible_id")
    private TipoCombustible tipoCombustible;

    @ManyToOne
    @JoinColumn(name = "unidad_id")
    private Unidad unidad;

    /*@ManyToOne
    @JoinColumn(name = "accion_id")
    private Accion accion;*7

    /*@ManyToOne
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;*/

    @ManyToOne
    @JoinColumn(name = "categoria_institucion_id")
    private CategoriaInstitucion categoriaInstitucion;

    /*@ManyToOne
    @JoinColumn(name = "produccion_id")
    private Produccion produccion;*/
}