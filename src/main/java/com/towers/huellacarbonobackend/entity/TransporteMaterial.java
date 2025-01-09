package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transporte_material")
@AllArgsConstructor
@NoArgsConstructor
public class TransporteMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "viajes", nullable = false)
    private Integer viajes;

    @Column(name = "tramo", nullable = false)
    private String tramo;

    @Column(name = "peso_transportado", nullable = false)
    private Double pesoTransportado;

    @Column(name = "distancia_recorrida", nullable = false)
    private Double distanciaRecorrida;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_vehiculo_id", nullable = false)
    private TipoVehiculo tipoVehiculo;
}