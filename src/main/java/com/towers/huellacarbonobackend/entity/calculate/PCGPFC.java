package com.towers.huellacarbonobackend.entity.calculate;

import com.towers.huellacarbonobackend.entity.data.TipoPFC;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pcg_pfc")
public class PCGPFC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "formula", nullable = false)
    private String formula;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_pfc_id", nullable = false)
    private TipoPFC tipoPFC;
}