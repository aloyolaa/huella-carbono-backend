package com.towers.huellacarbonobackend.entity.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "logo")
public class Logo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "logo_file", nullable = false)
    private String logoFile;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
}