package com.towers.huellacarbonobackend.entity.calculate;

import com.towers.huellacarbonobackend.entity.data.TipoRefrigerante;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pcg_refrigerante")
public class PCGRefrigerante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "formula", nullable = false)
    private String formula;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "tipo_refrigerante_id", nullable = false)
    private TipoRefrigerante tipoRefrigerante;
}