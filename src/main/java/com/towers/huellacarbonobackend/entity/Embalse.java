package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "embalse")
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

    public Embalse(Long id, String nombre, String ubicacion, Zona zona, Double area, Integer periodoLibreHielo, Double fraccionAreaInundada) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.zona = zona;
        this.area = area;
        this.periodoLibreHielo = periodoLibreHielo;
        this.fraccionAreaInundada = fraccionAreaInundada;
    }
}