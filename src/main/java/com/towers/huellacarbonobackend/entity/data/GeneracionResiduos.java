package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "generacion_residuos")
@NoArgsConstructor
public class GeneracionResiduos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "anio_huella", nullable = false)
    private Integer anioHuella;

    @Column(name = "precipitacion", nullable = false)
    private Double precipitacion;

    @Column(name = "anio_inicio", nullable = false)
    private Integer anioInicio;

    @Column(name = "temperatura", nullable = false)
    private Double temperatura;

    @Column(name = "contenido_grasas", nullable = false)
    private Boolean contenidoGrasas;

    @Column(name = "tasa_crecimiento", nullable = false)
    private Double tasaCrecimiento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "condicion_seds_id", nullable = false)
    private CondicionSEDS condicionSEDS;

    @OneToMany(mappedBy = "generacionResiduos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneracionResiduosDetalle> generacionResiduosDetalles = new ArrayList<>();
}