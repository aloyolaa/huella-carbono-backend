package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transporte_vehiculo")
@AllArgsConstructor
@RequiredArgsConstructor
public class TransporteVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tramo", nullable = false)
    private String tramo;

    @Column(name = "distancia_recorrida", nullable = false)
    private Double distanciaRecorrida;

    @Column(name = "personas_viajando", nullable = false)
    private Integer personasViajando;

    @Column(name = "veces_recorrido", nullable = false)
    private Integer vecesRecorrido;

    @ManyToOne
    @JoinColumn(name = "tipo_transporte_id")
    private TipoTransporte tipoTransporte;
}