package com.example.lab6.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "numeros_casa")
public class NumeroCasa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "numero_objetivo", nullable = false)
    private Integer numeroObjetivo;

    @Column(name = "intentos")
    private Integer intentos = 0;

    @Column(name = "adivinado")
    private Boolean adivinado = false;

    public NumeroCasa() {
    }

    public NumeroCasa(Integer id, Usuario usuario, Integer numeroObjetivo, Integer intentos, Boolean adivinado) {
        this.setId(id);
        this.setUsuario(usuario);
        this.setNumeroObjetivo(numeroObjetivo);
        this.setIntentos(intentos);
        this.setAdivinado(adivinado);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getNumeroObjetivo() {
        return numeroObjetivo;
    }

    public void setNumeroObjetivo(Integer numeroObjetivo) {
        this.numeroObjetivo = numeroObjetivo;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Boolean getAdivinado() {
        return adivinado;
    }

    public void setAdivinado(Boolean adivinado) {
        this.adivinado = adivinado;
    }
}
