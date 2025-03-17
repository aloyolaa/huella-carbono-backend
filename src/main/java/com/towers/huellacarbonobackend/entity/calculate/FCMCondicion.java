package com.towers.huellacarbonobackend.entity.calculate;

import com.towers.huellacarbonobackend.entity.data.CondicionSEDS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fcm_condicion")
public class FCMCondicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "condicion_seds_id", nullable = false)
    private CondicionSEDS condicionSEDS;
}