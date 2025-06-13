package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransporteCasaTrabajo transporteCasaTrabajo = (TransporteCasaTrabajo) o;
        return Objects.equals(id, transporteCasaTrabajo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}