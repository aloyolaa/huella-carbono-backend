package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "detalle")
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "area")
    private String area;

    @Column(name = "suministro")
    private String suministro;

    @Column(name = "superficie")
    private Double superficie;

    @Column(name = "medidor")
    private String medidor;

    @Column(name = "descripcion")
    private String descripcion;

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

    @ManyToOne(cascade = {CascadeType.ALL})
    private TransporteMaterial transporteMaterial;

    @ManyToOne(cascade = {CascadeType.ALL})
    private TransporteVehiculo transporteVehiculo;

    @ManyToOne(cascade = {CascadeType.ALL})
    private ConsumoPapel consumoPapel;

    @ManyToOne(cascade = {CascadeType.ALL})
    private GeneracionIndirectaNF3 generacionIndirectaNF3;

    @ManyToOne(cascade = {CascadeType.ALL})
    private GeneracionResiduos generacionResiduos;

    @OneToMany(mappedBy = "detalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransporteCasaTrabajo> transporteCasaTrabajos = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Detalle detalle = (Detalle) o;
        return Objects.equals(id, detalle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}