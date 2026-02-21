package com.aluracursos.literalura.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Entidad JPA que representa la tabla de autores en la base de datos PostgreSQL.
 * Mapea los datos obtenidos de la API para su persistencia.
 */
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> listaLibros;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.anioNacimiento();
        this.fechaFallecimiento = datosAutor.anioFallecimiento();
    }

    @Override
    public String toString() {
        String librosStr = (listaLibros != null && !listaLibros.isEmpty())
                ? listaLibros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", ", "[", "]"))
                : "[Ningún libro registrado]"
        ;

        return """
                -------------------- AUTOR --------------------
                NOMBRE: %s
                AÑO DE NACIMIENTO: %s
                AÑO DE FALLECIMIENTO: %s
                LIBROS: %s
                -----------------------------------------------
                """.formatted(
                nombre,
                fechaNacimiento != null ? fechaNacimiento : "Desconocida",
                fechaFallecimiento != null ? fechaFallecimiento : "Desconocida",
                librosStr
        )
        ;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getFechaNacimiento() { return fechaNacimiento; }

    public void setFechaNacimiento(Integer fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Integer getFechaFallecimiento() { return fechaFallecimiento; }

    public void setFechaFallecimiento(Integer fechaFallecimiento) { this.fechaFallecimiento = fechaFallecimiento; }

    public List<Libro> getListaLibros() { return listaLibros; }

    public void setListaLibros(List<Libro> listaLibros) { this.listaLibros = listaLibros; }
}
