package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "generacion_residuos_data")
@AllArgsConstructor
@NoArgsConstructor
public class GeneracionResiduosData {
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

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "datos_generales_id", nullable = false)
    private DatosGenerales datosGenerales;
}