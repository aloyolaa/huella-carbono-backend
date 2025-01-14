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
    private Integer productosMadera;

    @Column(name = "productos_papel", nullable = false)
    private Integer productosPapel;

    @Column(name = "residuos", nullable = false)
    private Integer residuos;

    @Column(name = "textiles", nullable = false)
    private Integer textiles;

    @Column(name = "jardines", nullable = false)
    private Integer jardines;

    @Column(name = "paniales", nullable = false)
    private Integer paniales;

    @Column(name = "otros", nullable = false)
    private Integer otros;

    @ManyToOne(optional = false)
    @JoinColumn(name = "generacion_residuos_id", nullable = false)
    private GeneracionResiduos generacionResiduos;

}