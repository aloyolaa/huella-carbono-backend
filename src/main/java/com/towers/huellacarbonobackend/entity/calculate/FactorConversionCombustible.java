package com.towers.huellacarbonobackend.entity.calculate;

import com.towers.huellacarbonobackend.entity.data.TipoCombustible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "factor_conversion_combustible")
public class FactorConversionCombustible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "tipo_combustible_id", nullable = false)
    private TipoCombustible tipoCombustible;
}