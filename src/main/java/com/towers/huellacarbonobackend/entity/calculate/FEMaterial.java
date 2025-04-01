package com.towers.huellacarbonobackend.entity.calculate;

import com.towers.huellacarbonobackend.entity.data.TipoVehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fe_material", indexes = {
        @Index(name = "idx_fematerial", columnList = "tipo_vehiculo_id")
})
public class FEMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_vehiculo_id", nullable = false)
    private TipoVehiculo tipoVehiculo;
}