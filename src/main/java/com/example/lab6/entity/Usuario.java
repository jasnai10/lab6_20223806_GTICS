package com.example.lab6.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intencion> intenciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsignacionCancion> asignacionesCanciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NumeroCasa> numerosCasa;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

    public Usuario() {
    }

    public Usuario(Integer id, String nombre, String correo, String password, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }
}
