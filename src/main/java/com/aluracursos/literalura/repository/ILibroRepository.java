package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repositorio de Spring Data JPA para la entidad Libro.
 * Provee los métodos automáticos para interactuar con la base de datos (CRUD).
 */
public interface ILibroRepository extends JpaRepository<Libro, Long> {



}
