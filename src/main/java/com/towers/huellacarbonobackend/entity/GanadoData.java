package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ganado_data")
public class GanadoData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "temperatura", nullable = false)
    private Double temperatura;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "datos_generales_id", nullable = false)
    private DatosGenerales datosGenerales;

    public GanadoData(Double temperatura, DatosGenerales datosGenerales) {
        this.temperatura = temperatura;
        this.datosGenerales = datosGenerales;
    }
}