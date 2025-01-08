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
    @JoinColumn(name = "categoria_institucion_id")
    private CategoriaInstitucion categoriaInstitucion;

    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Clinker clinker;

    @ManyToOne(cascade = {CascadeType.ALL})
    private RefrigeranteInstalacion refrigeranteInstalacion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private RefrigeranteOperacion refrigeranteOperacion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private RefrigeranteDisposicion refrigeranteDisposicion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private FugaInstalacion fugaInstalacion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private FugaOperacion fugaOperacion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private FugaDisposicion fugaDisposicion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private PFCInstalacion pfcInstalacion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private PFCOperacion pfcOperacion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private PFCDisposicion pfcDisposicion;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Ganado ganado;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Fertilizante fertilizante;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Encalado encalado;

    @ManyToOne(cascade = {CascadeType.ALL})
    private SueloGestionado sueloGestionado;

    @ManyToOne(cascade = {CascadeType.ALL})
    private CultivoArroz cultivoArroz;

    @ManyToOne(cascade = {CascadeType.ALL})
    private QuemaBiomasa quemaBiomasa;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Embalse embalse;
}