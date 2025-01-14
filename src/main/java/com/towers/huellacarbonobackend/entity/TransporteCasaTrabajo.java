package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transporte_casa_trabajo")
@AllArgsConstructor
@NoArgsConstructor
public class TransporteCasaTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "descripcion_personal")
    private String descripcionPersonal;

    @Column(name = "trabajadores")
    private Integer trabajadores;

    @Column(name = "viajes_semanales")
    private Integer viajesSemanales;

    @Column(name = "dias_laborales")
    private Integer diasLaborales;

    @Column(name = "distancia_viaje")
    private Double distanciaViaje;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_movilidad_id", nullable = false)
    private TipoMovilidad tipoMovilidad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "detalle_id", nullable = false)
    private Detalle detalle;
}