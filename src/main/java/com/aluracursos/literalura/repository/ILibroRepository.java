package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Repositorio de Spring Data JPA para la entidad Libro.
 * Provee los métodos automáticos para interactuar con la base de datos (CRUD).
 */
public interface ILibroRepository extends JpaRepository<Libro, Long> {

    /**
     * Busca y devuelve una lista de libros que coincidan con el idioma especificado.
     * Utiliza Derived Queries de Spring Data JPA.
     *
     * @param idioma Sigla del idioma a buscar (por ejemplo, "es", "en").
     * @return Lista de libros escritos en el idioma indicado.
     */
    List<Libro> findAllByIdioma(String idioma);

    /**
     * Busca libros en la base de datos que estén escritos en un idioma especificado.
     * Utiliza JPQL (Java Persistence Query Language) para una sintaxis más limpia y legible.
     *
     * @param idioma idioma a consultar.
     * @return Una lista de libros que cumplen con la condición.
     */
    @Query("SELECT l FROM Libro l WHERE l.idioma ILIKE :idioma")
    List<Libro> getLibrosEscritosEnIdioma(String idioma);

}
