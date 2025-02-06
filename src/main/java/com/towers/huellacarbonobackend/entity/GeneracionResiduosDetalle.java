package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "generacion_residuos_detalle")
@AllArgsConstructor
@NoArgsConstructor
public class GeneracionResiduosDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Column(name = "productos_madera", nullable = false)
    private Double productosMadera;

    @Column(name = "productos_papel", nullable = false)
    private Double productosPapel;

    @Column(name = "residuos", nullable = false)
    private Double residuos;

    @Column(name = "textiles", nullable = false)
    private Double textiles;

    @Column(name = "jardines", nullable = false)
    private Double jardines;

    @Column(name = "paniales", nullable = false)
    private Double paniales;

    @Column(name = "otros", nullable = false)
    private Double otros;

    @ManyToOne(optional = false)
    @JoinColumn(name = "generacion_residuos_id", nullable = false)
    private GeneracionResiduos generacionResiduos;

}