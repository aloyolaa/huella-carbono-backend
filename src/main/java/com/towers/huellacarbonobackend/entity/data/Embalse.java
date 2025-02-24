package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "embalse")
@AllArgsConstructor
@NoArgsConstructor
public class Embalse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;

    @Column(name = "area", nullable = false)
    private Double area;

    @Column(name = "periodo_libre_hielo", nullable = false)
    private Integer periodoLibreHielo;

    @Column(name = "fraccion_area_inundada", nullable = false)
    private Double fraccionAreaInundada;
}