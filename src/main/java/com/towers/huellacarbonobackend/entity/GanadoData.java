package com.towers.huellacarbonobackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
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
}