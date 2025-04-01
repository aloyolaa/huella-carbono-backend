package com.towers.huellacarbonobackend.entity.calculate;

import com.towers.huellacarbonobackend.entity.data.TipoPFC;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pcg_pfc", indexes = {
        @Index(name = "idx_pcgpfc_tipo_pfc_id", columnList = "tipo_pfc_id")
})
public class PCGPFC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "formula", nullable = false)
    private String formula;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "tipo_pfc_id", nullable = false)
    private TipoPFC tipoPFC;
}