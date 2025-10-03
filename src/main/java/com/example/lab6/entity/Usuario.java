package com.example.lab6.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
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
        this.setId(id);
        this.setNombre(nombre);
        this.setCorreo(correo);
        this.setPassword(password);
        this.setRol(rol);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Intencion> getIntenciones() {
        return intenciones;
    }

    public void setIntenciones(List<Intencion> intenciones) {
        this.intenciones = intenciones;
    }

    public List<AsignacionCancion> getAsignacionesCanciones() {
        return asignacionesCanciones;
    }

    public void setAsignacionesCanciones(List<AsignacionCancion> asignacionesCanciones) {
        this.asignacionesCanciones = asignacionesCanciones;
    }

    public List<NumeroCasa> getNumerosCasa() {
        return numerosCasa;
    }

    public void setNumerosCasa(List<NumeroCasa> numerosCasa) {
        this.numerosCasa = numerosCasa;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
