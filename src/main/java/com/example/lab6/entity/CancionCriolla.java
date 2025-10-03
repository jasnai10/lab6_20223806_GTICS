package com.example.lab6.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "canciones_criollas")
public class CancionCriolla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "letra")
    private String letra;

    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL)
    private List<AsignacionCancion> asignaciones;
}
