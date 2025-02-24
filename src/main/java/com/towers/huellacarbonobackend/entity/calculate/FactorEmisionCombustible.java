package com.towers.huellacarbonobackend.entity.calculate;

import com.towers.huellacarbonobackend.entity.data.TipoCombustible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "factor_emision_combustible")
public class FactorEmisionCombustible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "co2", nullable = false)
    private Double co2;

    @Column(name = "ch4", nullable = false)
    private Double ch4;

    @Column(name = "n2o", nullable = false)
    private Double n2o;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "tipo_combustible_id", nullable = false)
    private TipoCombustible tipoCombustible;
}