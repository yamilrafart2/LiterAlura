package com.aluracursos.literalura.model;

import com.aluracursos.literalura.service.ConsultaMyMemory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Entidad JPA que representa la tabla de libros en la base de datos PostgreSQL.
 * Mapea los datos obtenidos de la API para su persistencia.
 */
@Entity
@Table(name = "libros")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String titulo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(columnDefinition = "TEXT")
    private List<String> resumen;

    private String idioma;
    private Double totalDescargas;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.listaAutores().getFirst());
        this.resumen = datosLibro.resumen()
                .stream()
                .map(ConsultaMyMemory::obtenerTraduccion)
                .collect(Collectors.toList())
        ;
        this.idioma = datosLibro.listaIdiomas().getFirst();
        this.totalDescargas = OptionalDouble.of(datosLibro.totalDescargas()).orElse(0.0);
    }

    @Override
    public String toString() {
        return
                """
                -------------------- LIBRO --------------------
                TÍTULO: %s
                AUTOR: %s
                IDIOMA: %s
                DESCARGAS: %.0f
                RESUMEN: %s
                -----------------------------------------------
                """.formatted(
                        titulo,
                        (autor != null ? autor.getNombre() : "Desconocido"),
                        idioma,
                        totalDescargas,
                        resumen != null && !resumen.isEmpty() ? resumen.get(0) : "Sin resumen"
                );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getResumen() {
        return resumen;
    }

    public void setResumen(List<String> resumen) {
        this.resumen = resumen;
    }

    public Double getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(Double totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    public Autor getAutor() { return autor; }

    public void setAutor(Autor autor) { this.autor = autor; }

    public String getIdioma() { return idioma; }

    public void setIdioma(String idioma) { this.idioma = idioma; }
}
