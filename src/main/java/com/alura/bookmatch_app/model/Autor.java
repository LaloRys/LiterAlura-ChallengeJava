package com.alura.bookmatch_app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private Integer anioDeNacimiento;  // Usar "anio" en lugar de "año"

    @Column
    private Integer anioDeFallecimiento;  // Usar "anio" en lugar de "año"

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;
    // Constructor, getters y setters
    public Autor() {}
    public Autor(String nombre, Integer anioDeNacimiento, Integer anioDeFallecimiento) {
        this.nombre = nombre;
        this.anioDeNacimiento = anioDeNacimiento;
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Integer getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Integer anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
