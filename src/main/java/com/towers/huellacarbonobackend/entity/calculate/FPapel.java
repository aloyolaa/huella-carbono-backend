package com.towers.huellacarbonobackend.entity.calculate;

import com.towers.huellacarbonobackend.entity.data.TipoHoja;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "f_papel")
public class FPapel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "factor_a", nullable = false)
    private Double factorA;

    @Column(name = "factor_b", nullable = false)
    private Double factorB;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "tipo_hoja_id", nullable = false)
    private TipoHoja tipoHoja;
}